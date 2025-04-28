package hayashi.userservice.infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOperations;
    private final ObjectMapper objectMapper;

    public void setValueForKey(String key, String val) {
        valueOperations.set(key, val);
    }

    public void setValueForKey(String key, String val, long timeout) {
        valueOperations.set(key, val, timeout, TimeUnit.MILLISECONDS);
    }

    public void setValueForKey(String key, Object val) {
        try {
            valueOperations.set(key, objectMapper.writeValueAsString(val));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValueForKey(String key, Object val, long timeout) {
        try {
            valueOperations.set(key, objectMapper.writeValueAsString(val), timeout, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValueForKey(String key) {
        Object value = valueOperations.get(key);
        return (String) value;
    }

    public <T> Optional<T> getValueForKey(String key, Class<T> classType) {
        String value = (String) valueOperations.get(key);

        if (StringUtils.isEmpty(value)) return Optional.empty();

        try {
            return Optional.ofNullable(objectMapper.readValue(value, classType));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByKey(String key) {
        valueOperations.getAndDelete(key);
    }

    public void deleteKeysByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);

        if (!keys.isEmpty()) redisTemplate.delete(keys);
    }
}
