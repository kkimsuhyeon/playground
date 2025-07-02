package hayashi.userservice.application.command;

import lombok.Value;

@Value(staticConstructor = "of")
public class JoinUserCommand {
    String name;
    String email;
    String password;
}
