package hayashi.apigateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorResponseWriter {

    private final ObjectMapper objectMapper;

    public Mono<Void> writeErrorResponse(ServerHttpResponse response, ErrorInfo errorInfo, String path, String method) {
        if (response.isCommitted()) {
            log.warn("Response already committed, cannot write error response");
            return Mono.empty();
        }

        response.setStatusCode(errorInfo.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().add("Cache-Control", "no-cache");

        ErrorResponse errorResponse = ErrorResponse.from(errorInfo, path, method);

        try {
            String jsonBody = objectMapper.writeValueAsString(errorResponse);
            log.debug("Writing error response [{}] for {} {}", errorResponse.getCode(), method, path);

            return writeJsonToResponse(response, jsonBody);
        } catch (Exception e) {
            log.error("Failed to serialize error response for {} {}", method, path, e);

            return writeFallbackResponse(response, errorInfo);
        }
    }

    private Mono<Void> writeJsonToResponse(ServerHttpResponse response, String jsonBody) {
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer buffer = bufferFactory.wrap(jsonBody.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Flux.just(buffer));
    }

    private Mono<Void> writeFallbackResponse(ServerHttpResponse response, ErrorInfo errorInfo) {
        String fallbackResponse = String.format(
                "{\"code\":\"%s\",\"message\":\"%s\",\"timestamp\":%d}",
                errorInfo.getCode(), errorInfo.getMessage(), System.currentTimeMillis()
        );
        DataBuffer buffer = response.bufferFactory().wrap(fallbackResponse.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
