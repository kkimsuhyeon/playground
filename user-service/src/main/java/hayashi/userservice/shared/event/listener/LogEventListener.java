package hayashi.userservice.shared.event.listener;

import hayashi.userservice.adapter.out.external.client.LogServiceClient;
import hayashi.userservice.shared.event.ErrorLogEvent;
import hayashi.userservice.shared.event.SuccessLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final LogServiceClient logServiceClient;

    @Async
    @EventListener
    public void handleSuccessLog(SuccessLogEvent event) {
        log.info("[handleSuccessLog]: {}", event.getData());
        try {
            logServiceClient.saveLog(event.getData());
        } catch (Exception e) {
            log.error("[handleSuccessLog]: fail to save log : ", e);
        }
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleErrorLog(ErrorLogEvent event) {
        try {
            logServiceClient.saveLog(event.getData());
        } catch (Exception e) {
            log.error("[handleErrorLog]: fail to save log : ", e);
        }
    }

}
