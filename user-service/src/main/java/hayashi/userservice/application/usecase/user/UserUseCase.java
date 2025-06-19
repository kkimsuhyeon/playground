package hayashi.userservice.application.usecase.user;

import hayashi.userservice.domain.command.JoinUserCommand;
import hayashi.userservice.domain.query.FindUserQuery;
import hayashi.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService userService;

    public void join(JoinUserCommand command) {
    }

    public void find(FindUserQuery query) {

    }

}
