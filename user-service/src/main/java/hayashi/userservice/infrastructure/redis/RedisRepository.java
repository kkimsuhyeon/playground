package hayashi.userservice.infrastructure.redis;

import hayashi.userservice.config.redis.RedisDatabaseType;
import hayashi.userservice.config.redis.RedisTemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class RedisRepository {

    private final RedisTemplateFactory redisTemplateFactory;

    public void setValueForKey(RedisDatabaseType type, String key, String value) {
        redisTemplateFactory.getValueOps(type).set(key, value);
    }

    public void setValueForKey(RedisDatabaseType type, String key, String value, long timeout) {
        redisTemplateFactory.getValueOps(type).set(key, value, timeout);
    }

}
