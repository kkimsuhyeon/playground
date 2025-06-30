package hayashi.userservice.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER001", "유저가 존재하지 않습니다"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
