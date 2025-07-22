package hayashi.userservice.config.security.service;

import hayashi.userservice.config.security.token.JwtTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserService authUserService;

    public Authentication getAuthentication(JwtTokenPayload payload) {

        UserDetails userDetails = authUserService.loadUserById(payload.getId());
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
