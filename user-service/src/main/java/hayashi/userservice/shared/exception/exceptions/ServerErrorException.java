package hayashi.userservice.shared.exception.exceptions;

import hayashi.userservice.shared.exception.BusinessException;
import hayashi.userservice.shared.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ServerErrorException extends BusinessException {

    private final String description;

    public ServerErrorException(String description) {
        super(ErrorCode.SERVER_ERROR);
        this.description = description;
    }
}
