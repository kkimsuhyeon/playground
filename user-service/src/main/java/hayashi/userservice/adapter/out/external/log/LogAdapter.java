package hayashi.userservice.adapter.out.external.log;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import hayashi.userservice.application.port.LogPort;
import hayashi.userservice.shared.client.HttpClientAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogAdapter implements LogPort {

    private final WebClient webClient;
    private final HttpClientAdapter httpClientAdapter;

    public LogAdapter(@Qualifier("logServiceWebClient") WebClient webClient, HttpClientAdapter httpClientAdapter) {
        this.webClient = webClient;
        this.httpClientAdapter = httpClientAdapter;
    }

    @Override
    public Mono<Void> save(RequestSaveLog request) {
        String uri = UriComponentsBuilder.fromUriString("")
                .path("/api/v1/logs")
                .toUriString();

        return httpClientAdapter.get(webClient, uri, Void.class);
    }
}
