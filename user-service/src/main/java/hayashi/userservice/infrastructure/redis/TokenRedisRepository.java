package hayashi.userservice.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.common.redis.TokenRedisTemplateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {

    @Value("${jwt.expiration}")
    private long expiration;

    private final TokenRedisTemplateProvider redisTemplateProvider;
    private final ObjectMapper objectMapper;

    public void saveToken(String key, String token) {
        getTemplate().opsForValue().set(key, token, expiration);
    }

    public String getTokenByKey(String key) {
        Object value = getTemplate().opsForValue().get(key);
        return (String) value;
    }

    public void deleteTokenByKey(String key) {
        getTemplate().opsForValue().getAndDelete(key);
    }

    private RedisTemplate<String, Object> getTemplate() {
        return redisTemplateProvider.getTemplate();
    }
}
