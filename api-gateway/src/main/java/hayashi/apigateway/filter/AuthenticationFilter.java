package hayashi.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.apigateway.security.JwtTokenValidator;
import hayashi.apigateway.security.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_INFO_HEADER = "X-User-Info";

    private final ObjectMapper objectMapper;
    private final JwtTokenValidator jwtTokenValidator;

    public AuthenticationFilter(ObjectMapper objectMapper, JwtTokenValidator jwtTokenValidator) {
        super(Config.class);
        this.objectMapper = objectMapper;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getURI().getPath();

            if (config.isOpenApi(requestPath)) return chain.filter(exchange);

            if (!request.getHeaders().containsKey(AUTHORIZATION_HEADER)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.replace(BEARER_PREFIX, "");
            if (!jwtTokenValidator.tokenValidation(token)) {
                return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
            }

            UserInfo userInfo = jwtTokenValidator.extractUserInfo(token);
            String userInfoJson = userInfo.toJson(objectMapper);

            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header(USER_INFO_HEADER, userInfoJson)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = (String.format("{\"message\": \"%s\"}", message)).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }


    public static class Config {

        private final AntPathMatcher pathMatcher = new AntPathMatcher();

        private List<String> openApiEndpoints = List.of(
                "/api/v*/users/login",
                "/api/v*/users/join",
                "/api/v*/users/token",
                "/actuator/**"
        );

        public boolean isOpenApi(String path) {
            return openApiEndpoints.stream()
                    .anyMatch(endpoint -> pathMatcher.match(endpoint, path));
        }
    }

}
