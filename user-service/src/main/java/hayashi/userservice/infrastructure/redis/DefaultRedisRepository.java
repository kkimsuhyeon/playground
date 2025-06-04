package hayashi.userservice.infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.common.redis.DefaultRedisTemplateProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DefaultRedisRepository {

    private final DefaultRedisTemplateProvider redisTemplateProvider;
    private final ObjectMapper objectMapper;

    public void setValueForKey(String key, String val) {
        getTemplate()
                .opsForValue()
                .set(key, val);
    }

    public void setValueForKey(String key, String val, long timeout) {
        getTemplate()
                .opsForValue()
                .set(key, val, timeout, TimeUnit.MILLISECONDS);
    }

    public void setValueForKey(String key, Object val) {
        try {
            getTemplate()
                    .opsForValue()
                    .set(key, objectMapper.writeValueAsString(val));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValueForKey(String key, Object val, long timeout) {
        try {
            getTemplate()
                    .opsForValue()
                    .set(key, objectMapper.writeValueAsString(val), timeout, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValueForKey(String key) {
        Object value = getTemplate()
                .opsForValue()
                .get(key);

        return (String) value;
    }

    public <T> Optional<T> getValueForKey(String key, Class<T> classType) {
        String value = (String) getTemplate()
                .opsForValue()
                .get(key);

        if (StringUtils.isEmpty(value)) return Optional.empty();

        try {
            return Optional.ofNullable(objectMapper.readValue(value, classType));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByKey(String key) {
        getTemplate()
                .opsForValue()
                .getAndDelete(key);
    }

    public void deleteKeysByPattern(String pattern) {
        Set<String> keys = getTemplate().keys(pattern);
        if (!keys.isEmpty()) getTemplate().delete(keys);
    }

    private RedisTemplate<String, Object> getTemplate() {
        return redisTemplateProvider.getTemplate();
    }

}
