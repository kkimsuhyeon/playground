package hayashi.userservice.application.usecase.user;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.application.mapper.UserMapper;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinUseCaseImpl implements JoinUseCase {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserEntity join(JoinUserCommand command) {
        UserEntity entity = userMapper.toEntity(command);
        userService.create(entity);

        return entity;
    }
}
