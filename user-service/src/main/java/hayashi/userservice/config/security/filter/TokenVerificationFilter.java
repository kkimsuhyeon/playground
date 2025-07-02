package hayashi.userservice.config.security.filter;

import hayashi.userservice.config.security.service.AuthenticationService;
import hayashi.userservice.config.security.token.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        log.info("TokenVerificationFilter start");

        if (token != null && jwtTokenProvider.tokenValidation(token)) {
            Authentication authentication = authenticationService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("TokenVerificationFilter success");
        }

        filterChain.doFilter(request, response);
    }
}
