package hayashi.userservice.global.generator;

import com.github.f4b6a3.tsid.TsidCreator;
import hayashi.userservice.global.annotation.TsId;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Arrays;

@Slf4j
public class TsIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object entity) {
        String prefix = extractPrefix(session, entity);
        long tsId = TsidCreator.getTsid().toLong();

        log.info("Generate tsId for entity {}", entity);
        log.info("Generate tsId: {}", tsId);

        return prefix + tsId;
    }

    private String extractPrefix(SharedSessionContractImplementor session, Object entity) {
        Class<?> entityClass = entity.getClass();

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter((field) -> field.isAnnotationPresent(TsId.class))
                .findFirst()
                .map((field) -> field.getAnnotation(TsId.class).prefix())
                .orElse("");
    }
}
