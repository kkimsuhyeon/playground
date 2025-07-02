package hayashi.userservice.shared.exception.exceptions;

import hayashi.userservice.shared.exception.BusinessException;
import hayashi.userservice.shared.exception.ErrorCode;

public class PasswordIncorrectException extends BusinessException {

    public PasswordIncorrectException() {
        super(ErrorCode.PASSWORD_INCORRECT);
    }
}
