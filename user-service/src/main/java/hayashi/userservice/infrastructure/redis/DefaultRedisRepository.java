package hayashi.userservice.infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hayashi.userservice.config.redis.DefaultRedisTemplateProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
            throw new RuntimeException("Error processing JSON for Redis operation", e);
        }
    }

    public void setValueForKey(String key, Object val, long timeout) {
        try {
            getTemplate()
                    .opsForValue()
                    .set(key, objectMapper.writeValueAsString(val), timeout, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for Redis operation", e);
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
        RedisConnection connection = Objects.requireNonNull(getTemplate().getConnectionFactory()).getConnection();
        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(1000)
                .build();

        try (Cursor<byte[]> cursor = connection.keyCommands().scan(options)) {
            Set<byte[]> keys = new HashSet<>();
            while (cursor.hasNext()) keys.add(cursor.next());

            if (!keys.isEmpty()) getTemplate().delete(keys.stream()
                    .map(String::new)
                    .collect(Collectors.toSet())
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete keys by pattern", e);
        }
    }

    private RedisTemplate<String, Object> getTemplate() {
        return redisTemplateProvider.getTemplate();
    }

}
