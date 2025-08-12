package hayashi.userservice.shared.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HttpClientAdapterImpl implements HttpClientAdapter {

    @Override
    public <T> Mono<T> get(WebClient webClient, String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    @Override
    public <T> Mono<T> get(WebClient webClient, String url, ParameterizedTypeReference<T> responseType) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }
}
