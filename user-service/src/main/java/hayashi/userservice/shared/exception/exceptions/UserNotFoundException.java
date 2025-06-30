package hayashi.userservice.shared.exception.exceptions;


import hayashi.userservice.shared.exception.BusinessException;
import hayashi.userservice.shared.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
