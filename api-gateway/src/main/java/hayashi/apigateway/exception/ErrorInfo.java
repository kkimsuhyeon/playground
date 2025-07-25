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

}
