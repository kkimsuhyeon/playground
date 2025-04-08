package hayashi.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final ObjectMapper objectMapper;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public AuthenticationFilter(ObjectMapper objectMapper) {
        super(Config.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (config.isOpenApi(request.getURI().getPath())) {
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(AUTHORIZATION_HEADER)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange.mutate().build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = (String.format("{\"message\": \"%s\"}", "test")).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }


    public static class Config {
        private List<String> openApiEndpoints = List.of(
                "/api/v*/users/login",
                "/api/v*/users/join",
                "/actuator/**"
        );

        public boolean isOpenApi(String path) {
            return openApiEndpoints.stream()
                    .anyMatch(path::matches);
        }
    }

}
