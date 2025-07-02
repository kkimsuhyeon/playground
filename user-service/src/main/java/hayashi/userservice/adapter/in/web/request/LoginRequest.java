package hayashi.userservice.adapter.in.web.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Valid
@Data
public class LoginRequest {

    @Schema(description = "이메일")
    @NotBlank
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @Schema(description = "패스워드")
    @NotBlank
    private String password;
}
