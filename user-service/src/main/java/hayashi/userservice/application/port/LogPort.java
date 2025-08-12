package hayashi.userservice.application.port;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import reactor.core.publisher.Mono;

public interface LogPort {

    Mono<Void> save(RequestSaveLog request);
}
