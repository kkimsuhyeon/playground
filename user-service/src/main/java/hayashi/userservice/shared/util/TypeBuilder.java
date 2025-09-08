package hayashi.userservice.shared.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TypeBuilder {

    private final ObjectMapper objectMapper;

    private TypeFactory getTypeFactory() {
        return objectMapper.getTypeFactory();
    }

    public <T> JavaType listOf(Class<T> type) {
        return getTypeFactory().constructCollectionType(List.class, type);
    }

    public <K, V> JavaType mapOf(Class<K> keyType, Class<V> valueType) {
        return getTypeFactory().constructMapType(Map.class, keyType, valueType);
    }

    public <T> JavaType setOf(Class<T> type) {
        return getTypeFactory().constructCollectionType(Set.class, type);
    }

    public JavaType nested(Class<?> rawType, JavaType... typeArguments) {
        return getTypeFactory().constructParametricType(rawType, typeArguments);
    }
}
