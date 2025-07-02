package hayashi.userservice.application.usecase.token;


import hayashi.userservice.adapter.out.redis.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUseCaseImpl implements TokenUseCase {

    private final TokenRedisRepository tokenRedisRepository;

    @Override
    public String getToken(String key) {
        return tokenRedisRepository.getTokenByKey(key);
    }

    @Override
    public void deleteToken(String key) {
        tokenRedisRepository.deleteTokenByKey(key);
    }

}
