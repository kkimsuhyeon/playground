package hayashi.userservice.application.port;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import hayashi.userservice.shared.client.HttpClientResponse;
import reactor.core.publisher.Mono;

public interface LogPort {

    Mono<HttpClientResponse<Void>> save(RequestSaveLog request);
}
