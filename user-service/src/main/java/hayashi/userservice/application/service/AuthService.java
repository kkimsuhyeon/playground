package hayashi.userservice.application.service;


import hayashi.userservice.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String createAccessToken() {
        return jwtTokenProvider.createToken();
    }
}
