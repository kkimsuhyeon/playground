package hayashi.userservice.shared.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpClientAdapterImpl implements HttpClientAdapter {

    private final ObjectMapper objectMapper;

    @Override
    public <REQUEST, RESPONSE> Mono<HttpClientResponse<RESPONSE>> execute(WebClient webClient, HttpClientRequest<REQUEST> request, Class<RESPONSE> responseType) {
        return buildRequestSpec(webClient, request)
                .exchangeToMono((response) -> processResponse(response, responseType));
    }

    @Override
    public <REQUEST, RESPONSE> Mono<HttpClientResponse<RESPONSE>> execute(WebClient webClient, HttpClientRequest<REQUEST> request, JavaType responseType) {
        return buildRequestSpec(webClient, request)
                .exchangeToMono((response) -> processResponse(response, responseType));
    }

    private WebClient.RequestHeadersSpec<?> buildRequestSpec(WebClient webClient, HttpClientRequest<?> request) {
        WebClient.RequestBodySpec spec = webClient
                .method(request.getMethod())
                .uri(request.getUri())
                .headers(headers -> {
                    if (!request.getHeaders().isEmpty()) headers.addAll(request.getHeaders());
                });

        if (request.getBody() != null && !isBodylessMethod(request.getMethod())) {
            return spec.bodyValue(request.getBody());
        }

        return spec;
    }

    private boolean isBodylessMethod(HttpMethod method) {
        return method == HttpMethod.GET || method == HttpMethod.DELETE || method == HttpMethod.HEAD;
    }

    private <T> Mono<HttpClientResponse<T>> processResponse(ClientResponse response, Class<T> responseType) {
        return processResponseInternal(response, (rawBody) -> parseBody(rawBody, responseType));
    }

    private <T> Mono<HttpClientResponse<T>> processResponse(ClientResponse response, JavaType responseType) {
        return processResponseInternal(response, (rawBody) -> parseBody(rawBody, responseType));
    }

    private <T> Mono<HttpClientResponse<T>> processResponseInternal(ClientResponse response, BodyParser<T> bodyParser) {

        HttpStatus httpStatus = (HttpStatus) response.statusCode();
        HttpHeaders httpHeaders = response.headers().asHttpHeaders();

        if (httpStatus.is2xxSuccessful()) {
            return handleSuccessResponse(response, httpStatus, httpHeaders, bodyParser);
        }

        return handleErrorResponse(response, httpStatus, httpHeaders);
    }

    private <T> Mono<HttpClientResponse<T>> handleSuccessResponse(ClientResponse response, HttpStatus status, HttpHeaders headers, BodyParser<T> bodyParser) {
        return response.bodyToMono(String.class)
                .flatMap(rawBody -> {
                    try {
                        T parsedBody = bodyParser.parse(rawBody);
                        return Mono.just(HttpClientResponse.<T>builder()
                                .status(status)
                                .headers(headers)
                                .body(parsedBody)
                                .rawBody(rawBody)
                                .build()
                        );
                    } catch (JsonProcessingException e) {
                        log.error("JSON parsing failed for response body: {}, error: {}", rawBody, e.getMessage());
                        return Mono.error(e);
                    } catch (Exception e) {
                        log.error("Failed to parse response body: {}", rawBody, e);
                        return Mono.error(e);
                    }
                }).switchIfEmpty(Mono.defer(() -> Mono.just(HttpClientResponse.<T>builder()
                        .status(status)
                        .headers(headers)
                        .build()
                )));
    }

    private <T> Mono<HttpClientResponse<T>> handleErrorResponse(ClientResponse response, HttpStatus status, HttpHeaders headers) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    log.error("Error Response: Status: {}, Body: {}", status, errorBody);

                    HttpClientResponse<T> errorResponse = HttpClientResponse.<T>builder()
                            .status(status)
                            .headers(headers)
                            .rawBody(errorBody)
                            .build();

                    return Mono.just(errorResponse);
                }).switchIfEmpty(Mono.defer(() -> {
                    HttpClientResponse<T> errorResponse = HttpClientResponse.<T>builder()
                            .status(status)
                            .headers(headers)
                            .rawBody("no body")
                            .build();

                    return Mono.just(errorResponse);
                }));

    }

    @FunctionalInterface
    private interface BodyParser<T> {
        T parse(String rawBody) throws JsonProcessingException;
    }

    private <T> T parseBody(String rawBody, Class<T> responseType) throws JsonProcessingException {
        return objectMapper.readValue(rawBody, responseType);
    }

    @SuppressWarnings("unchecked")
    private <T> T parseBody(String rawBody, JavaType responseType) throws JsonProcessingException {
        return (T) objectMapper.readValue(rawBody, responseType);
    }
}
