package hayashi.userservice.config.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultRedisTemplateProvider extends BaseRedisTemplate implements TypedRedisTemplateProvider {

    private final RedisDatabaseType redisDatabaseType = RedisDatabaseType.DEFAULT;

    private final RedisTemplate<String, Object> template;

    public DefaultRedisTemplateProvider(RedisProperties redisProperties) {
        super(redisProperties);
        this.template = new RedisTemplate<>();

        RedisConnectionFactory factory = createFactory(redisDatabaseType);
        template.setConnectionFactory(factory);
        setSerializer(template);
        template.afterPropertiesSet();
    }

    @Override
    public RedisDatabaseType getType() {
        return this.redisDatabaseType;
    }

    @Override
    public RedisTemplate<String, Object> getTemplate() {
        return template;
    }
}
