package hayashi.userservice.adapter.in.web;

import hayashi.userservice.application.usecase.auth.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "인증관리[auth-controller]", description = "인증관련 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/token")
public class AuthController {

    private final AuthUseCase authUseCase;

    @Operation(summary = "토큰 생성", description = "토큰 생성 API")
    @PostMapping("/{key}")
    public ResponseEntity<String> createToken(@Parameter(description = "고유키") @PathVariable(name = "key") @NotBlank String key) {
        return new ResponseEntity<>(authUseCase.createToken(key), HttpStatus.OK);
    }

    @Operation(summary = "토큰 조회", description = "토큰 조회 API")
    @GetMapping("/{key}")
    public ResponseEntity<String> getToken(@Parameter(description = "고유키") @PathVariable(name = "key") @NotBlank String key) {
        return new ResponseEntity<>(authUseCase.getToken(key), HttpStatus.OK);
    }

    @Operation(summary = "토큰 삭제", description = "토큰 삭제 API")
    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteToken(@Parameter(description = "고유키") @PathVariable(name = "key") @NotBlank String key) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
