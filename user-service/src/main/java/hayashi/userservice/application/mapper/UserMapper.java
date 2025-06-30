package hayashi.userservice.application.mapper;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(JoinUserCommand command) {
        return UserEntity.builder()
                .name(command.getName())
                .email(command.getEmail())
                .password(command.getPassword())
                .build();
    }
}
