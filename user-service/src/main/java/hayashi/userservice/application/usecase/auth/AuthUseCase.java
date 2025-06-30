package hayashi.userservice.application.usecase.auth;


import hayashi.userservice.adapter.out.redis.TokenRedisRepository;
import hayashi.userservice.adapter.out.token.JwtTokenInfo;
import hayashi.userservice.adapter.out.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRedisRepository tokenRedisRepository;

    public String createToken(String key) {
        JwtTokenInfo token = jwtTokenProvider.createToken(null);
        tokenRedisRepository.saveToken(key, token.getToken());

        return token.getToken();
    }

    public String getToken(String key) {
        return tokenRedisRepository.getTokenByKey(key);
    }

    public void deleteToken(String key) {
        tokenRedisRepository.deleteTokenByKey(key);
    }

}
