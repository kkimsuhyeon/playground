package hayashi.userservice.application.mapper;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(JoinUserCommand command) {
        return UserEntity.builder()
                .name(command.getName())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .build();
    }
}
