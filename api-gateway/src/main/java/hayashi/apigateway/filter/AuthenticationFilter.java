package hayashi.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.apigateway.exception.ErrorInfo;
import hayashi.apigateway.exception.ErrorResponseWriter;
import hayashi.apigateway.security.JwtTokenValidator;
import hayashi.apigateway.security.UserInfo;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_INFO_HEADER = "X-User-Info";

    private final ObjectMapper objectMapper;
    private final JwtTokenValidator jwtTokenValidator;
    private final ErrorResponseWriter errorResponseWriter;

    public AuthenticationFilter(ObjectMapper objectMapper, JwtTokenValidator jwtTokenValidator, ErrorResponseWriter errorResponseWriter) {
        super(Config.class);
        this.objectMapper = objectMapper;
        this.jwtTokenValidator = jwtTokenValidator;
        this.errorResponseWriter = errorResponseWriter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String requestPath = request.getURI().getPath();
            String requestMethod = request.getMethod().name();

            if (config.isOpenApi(requestPath)) return chain.filter(exchange);

            if (!request.getHeaders().containsKey(AUTHORIZATION_HEADER)) {
                ErrorInfo errorInfo = new ErrorInfo(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "No Authorization header");
                return errorResponseWriter.writeErrorResponse(exchange.getResponse(), errorInfo, requestPath, requestMethod);
            }

            String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                ErrorInfo errorInfo = new ErrorInfo(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Invalid Authorization header format");
                return errorResponseWriter.writeErrorResponse(exchange.getResponse(), errorInfo, requestPath, requestMethod);
            }

            String token = authHeader.replace(BEARER_PREFIX, "");
            if (!jwtTokenValidator.tokenValidation(token)) {
                ErrorInfo errorInfo = new ErrorInfo(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Invalid token");
                return errorResponseWriter.writeErrorResponse(exchange.getResponse(), errorInfo, requestPath, requestMethod);
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
