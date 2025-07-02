package hayashi.userservice.adapter.in.web;

import hayashi.userservice.application.usecase.token.TokenUseCase;
import hayashi.userservice.shared.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "인증관리[auth-controller]", description = "인증관련 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/token")
public class TokenController {

    private final TokenUseCase tokenUseCase;

    @Operation(summary = "토큰 조회", description = "토큰 조회 API")
    @GetMapping("/{key}")
    public ResponseEntity<BaseResponse<String>> getToken(@Parameter(description = "고유키") @PathVariable(name = "key") @NotBlank String key) {

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(tokenUseCase.getToken(key)));
    }

    @Operation(summary = "토큰 삭제", description = "토큰 삭제 API")
    @DeleteMapping("/{key}")
    public ResponseEntity<BaseResponse<Void>> deleteToken(@Parameter(description = "고유키") @PathVariable(name = "key") @NotBlank String key) {
        tokenUseCase.deleteToken(key);

        return ResponseEntity
                .ok()
                .body(BaseResponse.success());
    }

}
