package hayashi.userservice.domain.command;

import hayashi.userservice.domain.model.UserEntity;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class JoinUserCommand {
    String name;
    String email;
    String password;

}
