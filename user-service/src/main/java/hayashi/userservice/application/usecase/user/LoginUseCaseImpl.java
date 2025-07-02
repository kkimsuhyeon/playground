package hayashi.userservice.application.usecase.user;

import hayashi.userservice.adapter.out.redis.TokenRedisRepository;
import hayashi.userservice.config.security.token.JwtTokenInfo;
import hayashi.userservice.config.security.token.JwtTokenPayload;
import hayashi.userservice.config.security.token.JwtTokenProvider;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.service.UserService;
import hayashi.userservice.shared.exception.exceptions.PasswordIncorrectException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginUseCaseImpl implements LoginUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRedisRepository tokenRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public String login(String email, String password) {
        UserEntity user = userService.getByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordIncorrectException();
        }

        JwtTokenPayload payload = JwtTokenPayload.from(user);
        JwtTokenInfo token = jwtTokenProvider.createToken(payload);
        tokenRedisRepository.saveToken(user.getId(), token.getToken());

        user.updateLastLoginAt();

        return token.getToken();
    }
}
