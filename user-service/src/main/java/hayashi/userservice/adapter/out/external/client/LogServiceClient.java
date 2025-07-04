package hayashi.userservice.adapter.out.external.client;

import hayashi.userservice.adapter.out.external.dto.RequestSaveLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "log-service", url = "${external.log-service.url}")
public interface LogServiceClient {

    @PostMapping("/api/v1/logs")
    void saveLog(RequestSaveLog request);
}
