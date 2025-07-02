package hayashi.userservice.application.factory;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandFactory {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(JoinUserCommand command) {
        return UserEntity.builder()
                .name(command.getName())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .build();
    }
}
