package hayashi.userservice.interfaces.rest;

import hayashi.userservice.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "인증관리[auth-controller]", description = "인증관련 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "토큰 생성", description = "토큰 생성 API")
    @GetMapping("/token")
    public ResponseEntity<String> createAccessToken() {
        return new ResponseEntity<>(authService.createAccessToken(), HttpStatus.OK);
    }

}
