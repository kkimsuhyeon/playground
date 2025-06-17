package hayashi.userservice.config.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RedisTemplateFactory {

    private final Map<RedisDatabaseType, RedisTemplate<String, Object>> templateMap;

    public RedisTemplateFactory(List<TypedRedisTemplateProvider> redisTemplates) {
        this.templateMap = redisTemplates.stream()
                .collect(Collectors.toMap(TypedRedisTemplateProvider::getType, TypedRedisTemplateProvider::getTemplate));
    }

    public RedisTemplate<String, Object> getTemplate(RedisDatabaseType type) {
        return templateMap.get(type);
    }

    /**
     * 단일 데이터에 접근하여 다양한 연산을 수행합니다.
     */
    public ValueOperations<String, Object> getValueOps(RedisDatabaseType type) {
        return getTemplate(type).opsForValue();
    }

    /**
     * 리스트에 접근하여 다양한 연산을 수행합니다.
     */
    public ListOperations<String, Object> getListOps(RedisDatabaseType type) {
        return getTemplate(type).opsForList();
    }

}
