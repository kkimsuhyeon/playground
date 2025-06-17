package hayashi.userservice.config.redis;

import org.springframework.data.redis.core.RedisTemplate;

public interface TypedRedisTemplateProvider {

    RedisDatabaseType getType();

    RedisTemplate<String, Object> getTemplate();
}
