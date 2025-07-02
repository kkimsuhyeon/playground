package hayashi.userservice.config.security.service;

import hayashi.userservice.config.security.token.JwtTokenPayload;
import hayashi.userservice.config.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserService authUserService;
    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(String token) {
        JwtTokenPayload payload = jwtTokenProvider.getPayload(token);

        UserDetails userDetails = authUserService.loadUserById(payload.getId());
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
