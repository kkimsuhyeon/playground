package hayashi.userservice.application.service;


import hayashi.userservice.infrastructure.redis.TokenRedisRepository;
import hayashi.userservice.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRedisRepository tokenRedisRepository;

    public String createToken(String key) {
        String token = jwtTokenProvider.createToken();
        tokenRedisRepository.saveToken(key, token);

        return token;
    }

    public String getToken(String key) {
        return tokenRedisRepository.getTokenByKey(key);
    }

    public void deleteToken(String key) {
        tokenRedisRepository.deleteTokenByKey(key);
    }

}
