package hayashi.userservice.adapter.in.web;

import hayashi.userservice.adapter.in.web.request.JoinRequest;
import hayashi.userservice.adapter.in.web.request.LoginRequest;
import hayashi.userservice.adapter.in.web.request.UserFind;
import hayashi.userservice.application.usecase.user.JoinUseCase;
import hayashi.userservice.application.usecase.user.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저관리[user-controller]", description = "유저관리 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final JoinUseCase joinUseCase;
    private final LoginUseCase loginUseCase;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid JoinRequest request) {
        joinUseCase.join(request.toCommand());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = loginUseCase.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "유저 조회", description = "유저 조회 API")
    @GetMapping
    public ResponseEntity<Void> getUsers(@Valid UserFind find) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "유저 상세 조회", description = "유저 상세 조회 API")
    @GetMapping("/{userId}")
    public ResponseEntity<Void> getUser(@PathVariable(name = "userId") String userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
