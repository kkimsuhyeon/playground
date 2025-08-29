package hayashi.userservice.config.client;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;

@Slf4j
@Configuration
public class WebClientConfig {

    @Value("${external.log-service.url}")
    private String logServiceUrl;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return createBaseWebClientBuilder();
    }

    @Bean
    @Qualifier("logServiceWebClient")
    public WebClient logServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(logServiceUrl)
                .build();
    }

    private WebClient.Builder createBaseWebClientBuilder() {
        ConnectionProvider connectionProvider = ConnectionProvider
                .builder("defaultConnectionPool")
                .maxConnections(30) // 최대 연결 개수
                .maxIdleTime(Duration.ofMinutes(2)) // 최대 유휴 시간
                .maxLifeTime(Duration.ofMinutes(10)) // 최대 생존 시간
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(CONNECT_TIMEOUT_MILLIS, 10000) // 연결 타임아웃, 10초
                .doOnConnected(conn -> conn
                        .addHandlerFirst(new ReadTimeoutHandler(90)) // 읽기 타임아웃
                        .addHandlerLast(new WriteTimeoutHandler(60)) // 쓰기 타임아웃
                );

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configure -> {
                    configure.defaultCodecs().maxInMemorySize(20 * 1024 * 1024); // 메모리 버퍼 크기 20MB
                    configure.defaultCodecs().enableLoggingRequestDetails(true); // 요청 로깅
                })
                .build();

        return WebClient.builder()
                .defaultHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(org.springframework.http.HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunction.ofRequestProcessor(this::handleRequest))
                .filter(ExchangeFilterFunction.ofResponseProcessor(this::handleErrors))
                .filter(ExchangeFilterFunction.ofResponseProcessor(this::handleResponse))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(exchangeStrategies);
    }

    private Mono<ClientRequest> handleRequest(ClientRequest request) {
        log.info("Request: {} {}", request.method(), request.url());
        return Mono.just(request);
    }

    private Mono<ClientResponse> handleResponse(ClientResponse response) {
        log.info("Response Status: {}", response.statusCode());
        return Mono.just(response);
    }

    private Mono<ClientResponse> handleErrors(ClientResponse response) {
        if (response.statusCode().isError()) {
            log.error("API Error: {} - {}", response.statusCode(), response.request().getURI());
        }
        return Mono.just(response);

    }

}
