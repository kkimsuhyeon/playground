package hayashi.userservice.shared.client;

import com.fasterxml.jackson.databind.JavaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface HttpClientAdapter {

    <REQUEST, RESPONSE> Mono<HttpClientResponse<RESPONSE>> execute(WebClient webClient, HttpClientRequest<REQUEST> request, Class<RESPONSE> responseType);

    <REQUEST, RESPONSE> Mono<HttpClientResponse<RESPONSE>> execute(WebClient webClient, HttpClientRequest<REQUEST> request, JavaType responseType);
}
