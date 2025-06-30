package hayashi.userservice.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUE", "입력값이 올바르지 않습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER001", "유저가 존재하지 않습니다"),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "USER002", "비밀번호가 일치하지 않습니다"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER ERROR", "서버 에러"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
