package hayashi.apigateway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorInfo {

    private final HttpStatus status;
    private final String code;
    private final String message;

    public static ErrorInfo unauthorized(String message) {
        return new ErrorInfo(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", message);
    }

    public static ErrorInfo forbidden(String message) {
        return new ErrorInfo(HttpStatus.FORBIDDEN, "FORBIDDEN", message);
    }

    public static ErrorInfo badRequest(String message) {
        return new ErrorInfo(HttpStatus.BAD_REQUEST, "BAD_REQUEST", message);
    }

    public static ErrorInfo serviceUnavailable(String message) {
        return new ErrorInfo(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", message);
    }

    public static ErrorInfo gatewayTimeout(String message) {
        return new ErrorInfo(HttpStatus.GATEWAY_TIMEOUT, "GATEWAY_TIMEOUT", message);
    }

    public static ErrorInfo notFound(String message) {
        return new ErrorInfo(HttpStatus.NOT_FOUND, "NOT_FOUND", message);
    }

    public static ErrorInfo internalServerError(String message) {
        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", message);
    }
}
