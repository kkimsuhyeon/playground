package hayashi.userservice.shared.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface HttpClientAdapter {

    <T> Mono<T> get(WebClient webClient, String url, Class<T> responseType);

    <T> Mono<T> get(WebClient webClient, String url, ParameterizedTypeReference<T> responseType);

}
