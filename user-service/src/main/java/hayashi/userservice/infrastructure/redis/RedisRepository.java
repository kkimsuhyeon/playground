package hayashi.userservice.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.common.redis.RedisDatabaseType;
import hayashi.userservice.common.redis.RedisTemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class RedisRepository {

    private final RedisTemplateFactory redisTemplateFactory;
    private final ObjectMapper objectMapper;

    public void setValueForKey(RedisDatabaseType type, String key, String value) {
        redisTemplateFactory.getValueOps(type).set(key, value);
    }

    public void setValueForKey(RedisDatabaseType type, String key, String value, long timeout) {
        redisTemplateFactory.getValueOps(type).set(key, value, timeout);
    }

}
