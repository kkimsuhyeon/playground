package hayashi.userservice.adapter.out.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "log-service")
public interface LogServiceClient {

    @PostMapping("/api/v1/logs")
    void saveLog();
}
