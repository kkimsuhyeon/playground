package hayashi.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.shared.dto.BaseResponse;
import hayashi.userservice.shared.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ErrorCode ERROR_CODE = ErrorCode.UNAUTHORIZED_ERROR;

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("SecurityAuthenticationEntryPoint error");

        BaseResponse<Object> fail = BaseResponse.fail(ERROR_CODE);

        response.setStatus(ERROR_CODE.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(fail));

    }
}
