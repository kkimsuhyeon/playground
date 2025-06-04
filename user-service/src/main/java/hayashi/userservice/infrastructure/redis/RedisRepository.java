package hayashi.userservice.infrastructure.redis;

import hayashi.userservice.common.redis.RedisTemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplateFactory redisTemplateFactory;
}
