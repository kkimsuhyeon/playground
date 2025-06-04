package hayashi.userservice.common.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultRedisTemplateProvider extends BaseRedisTemplate implements TypedRedisTemplateProvider {

    private final RedisDatabaseType redisDatabaseType = RedisDatabaseType.DEFAULT;

    @Override
    public RedisDatabaseType getType() {
        return this.redisDatabaseType;
    }

    @Override
    public RedisTemplate<String, Object> getTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        RedisConnectionFactory factory = createFactory(this.redisDatabaseType);
        template.setConnectionFactory(factory);
        setSerializer(template);

        return template;
    }
}
