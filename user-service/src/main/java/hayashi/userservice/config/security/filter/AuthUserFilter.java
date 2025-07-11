package hayashi.userservice.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.config.security.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserFilter extends OncePerRequestFilter {

    private static final String USER_INFO_HEADER = "X-User-Info";

    private final ObjectMapper objectMapper;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userInfoHeader = request.getHeader(USER_INFO_HEADER);

        if (userInfoHeader != null) {
            log.info("AuthUserFilter success");
        }

        filterChain.doFilter(request, response);
    }
}
