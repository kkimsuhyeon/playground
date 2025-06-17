package hayashi.userservice.config.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenRedisTemplateProvider extends BaseRedisTemplate implements TypedRedisTemplateProvider {

    private static final RedisDatabaseType redisDatabaseType = RedisDatabaseType.TOKEN;

    private final RedisTemplate<String, Object> template;

    public TokenRedisTemplateProvider(RedisProperties redisProperties) {
        super(redisProperties);
        this.template = new RedisTemplate<>();

        RedisConnectionFactory factory = createFactory(redisDatabaseType);
        template.setConnectionFactory(factory);
        setSerializer(template);
        template.afterPropertiesSet();
    }

    @Override
    public RedisDatabaseType getType() {
        return redisDatabaseType;
    }

    @Override
    public RedisTemplate<String, Object> getTemplate() {
        return template;
    }
}
