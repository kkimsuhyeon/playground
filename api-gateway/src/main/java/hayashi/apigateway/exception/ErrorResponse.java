package hayashi.apigateway.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private long timestamp;
    private String path;
    private String method;

    public static ErrorResponse from(ErrorInfo errorInfo, String path, String method) {
        return ErrorResponse.builder()
                .code(errorInfo.getCode())
                .message(errorInfo.getMessage())
                .timestamp(System.currentTimeMillis())
                .path(path)
                .method(method)
                .build();
    }
}
