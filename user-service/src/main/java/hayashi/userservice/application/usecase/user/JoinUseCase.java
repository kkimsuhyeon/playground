package hayashi.userservice.application.usecase.user;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;

public interface JoinUseCase {

    UserEntity join(JoinUserCommand command);
}
