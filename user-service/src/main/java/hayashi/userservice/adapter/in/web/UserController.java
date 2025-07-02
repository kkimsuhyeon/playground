package hayashi.userservice.adapter.in.web;

import hayashi.userservice.adapter.in.web.request.JoinRequest;
import hayashi.userservice.adapter.in.web.request.LoginRequest;
import hayashi.userservice.adapter.in.web.response.UserResponse;
import hayashi.userservice.application.usecase.user.JoinUseCase;
import hayashi.userservice.application.usecase.user.LoginUseCase;
import hayashi.userservice.application.usecase.user.UserQueryUseCase;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.shared.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저관리[user-controller]", description = "유저관리 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final JoinUseCase joinUseCase;
    private final LoginUseCase loginUseCase;
    private final UserQueryUseCase userQueryUseCase;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/join")
    public ResponseEntity<BaseResponse<UserResponse>> join(@RequestBody @Valid JoinRequest request) {
        UserEntity user = joinUseCase.join(request.toCommand());

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(UserResponse.from(user)));
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody @Valid LoginRequest request) {
        String token = loginUseCase.login(request.getEmail(), request.getPassword());

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(token));
    }

    @Operation(summary = "유저 조회", description = "유저 조회 API")
    @GetMapping
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getUsers(
            @Parameter(description = "이름", in = ParameterIn.QUERY) @RequestParam(required = false, name = "name") String name,
            @Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam(required = false, name = "email") String email,
            Pageable pageable
    ) {
        Page<UserResponse> response = userQueryUseCase.getUsers(name, email, pageable)
                .map(UserResponse::from);

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "유저 상세 조회", description = "유저 상세 조회 API")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> getUser(
            @Parameter(description = "유저아이디", required = true, in = ParameterIn.PATH) @PathVariable(name = "userId") String userId
    ) {
        UserEntity user = userQueryUseCase.getUser(userId);

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(UserResponse.from(user)));
    }
}
