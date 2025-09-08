package hayashi.userservice.application.usecase.user;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.application.factory.UserCommandFactory;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinUseCaseImpl implements JoinUseCase {

    private final UserCommandFactory userCommandFactory;
    private final UserService userService;

    @Override
    public UserEntity join(JoinUserCommand command) {
        UserEntity entity = userCommandFactory.toEntity(command);
        return userService.create(entity);
    }
}
