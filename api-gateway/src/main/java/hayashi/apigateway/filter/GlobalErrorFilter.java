package hayashi.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.apigateway.exception.ErrorInfo;
import hayashi.apigateway.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalErrorFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .doOnSuccess(aVoid -> log.info("Request processed successfully: {}", exchange.getRequest().getURI()))
                .onErrorResume(throwable -> {
                    log.error("Global error occurred for path: {} - {}", exchange.getRequest().getURI().getPath(), throwable.getMessage(), throwable);
                    return handleGlobalError(exchange, throwable);
                });
    }

    private Mono<Void> handleGlobalError(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            log.warn("Response already committed, cannot handle error");
            return Mono.error(throwable);
        }

        ErrorInfo errorInfo = determineErrorInfo(throwable);

        response.setStatusCode(errorInfo.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorInfo.getCode())
                .message(errorInfo.getMessage())
                .timestamp(System.currentTimeMillis())
                .path(exchange.getRequest().getPath().value())
                .method(exchange.getRequest().getMethod().name())
                .build();

        try {
            String body = objectMapper.writeValueAsString(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            log.error("Error serializing global error response", e);
            return createFallbackResponse(response, errorInfo);
        }
    }

    private ErrorInfo determineErrorInfo(Throwable throwable) {
        if (throwable instanceof ConnectException || (throwable.getMessage() != null && throwable.getMessage().contains("Connection refused"))) {
            return new ErrorInfo(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "백엔드 서비스에 연결할 수 없습니다.");
        }

        if (throwable instanceof TimeoutException || (throwable.getMessage() != null && throwable.getMessage().contains("timeout"))) {
            return new ErrorInfo(HttpStatus.GATEWAY_TIMEOUT, "GATEWAY_TIMEOUT", "요청 처리 시간이 초과되었습니다.");
        }

        if (throwable instanceof NotFoundException || (throwable.getMessage() != null && throwable.getMessage().contains("404"))) {
            return new ErrorInfo(HttpStatus.NOT_FOUND, "ROUTE_NOT_FOUND", "요청한 경로를 찾을 수 없습니다.");
        }

        if (throwable.getMessage() != null && throwable.getMessage().contains("503")) {
            return new ErrorInfo(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "서비스를 일시적으로 사용할 수 없습니다.");
        }

        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");
    }

    private Mono<Void> createFallbackResponse(ServerHttpResponse response, ErrorInfo errorInfo) {
        String fallbackResponse = String.format("{\"code\":\"%s\",\"message\":\"%s\",\"timestamp\":%d}", errorInfo.getCode(), errorInfo.getMessage(), System.currentTimeMillis());

        DataBuffer buffer = response.bufferFactory().wrap(fallbackResponse.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
