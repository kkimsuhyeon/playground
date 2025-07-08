package hayashi.userservice.application.usecase.token;


import hayashi.userservice.adapter.out.redis.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenUseCaseImpl implements TokenUseCase {

    private final TokenRedisRepository tokenRedisRepository;

    @Override
    @Transactional(readOnly = true)
    public String getToken(String key) {
        return tokenRedisRepository.getTokenByKey(key);
    }

    @Override
    @Transactional
    public void deleteToken(String key) {
        tokenRedisRepository.deleteTokenByKey(key);
    }

}
