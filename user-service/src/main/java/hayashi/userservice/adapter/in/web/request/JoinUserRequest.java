package hayashi.userservice.adapter.in.web.request;

import hayashi.userservice.application.command.JoinUserCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Valid
@Data
public class JoinUserRequest {

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "이메일")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    public JoinUserCommand toCommand() {
        return JoinUserCommand.of(name, email, password);
    }

}
