package hayashi.userservice.adapter.in.web.request;

import hayashi.userservice.application.command.JoinUserCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Valid
@Data
public class JoinRequest {

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "이메일")
    @NotBlank
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    public JoinUserCommand toCommand() {
        return JoinUserCommand.of(name, email, password);
    }

}
