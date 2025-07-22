package hayashi.userservice.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.config.security.CustomHttpServletRequestWrapper;
import hayashi.userservice.config.security.token.JwtTokenPayload;
import hayashi.userservice.config.security.token.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class TokenVerificationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_INFO_HEADER = "X-User-Info";

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("TokenVerificationFilter start");

        CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper(request);
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.replace(BEARER_PREFIX, "");
            if (jwtTokenProvider.tokenValidation(token)) {
                JwtTokenPayload payload = jwtTokenProvider.getPayload(token);
                wrappedRequest.addHeader(USER_INFO_HEADER, objectMapper.writeValueAsString(payload));

                log.info("TokenVerificationFilter success");
            }
        }

        filterChain.doFilter(wrappedRequest, response);
    }
}
