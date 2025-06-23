package hayashi.userservice.application.usecase.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginUseCaseImpl implements LoginUseCase {

    @Override
    public String login(String email, String password) {
        return "";
    }
}
