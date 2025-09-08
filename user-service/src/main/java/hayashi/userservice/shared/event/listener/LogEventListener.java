package hayashi.userservice.shared.event.listener;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import hayashi.userservice.application.port.LogPort;
import hayashi.userservice.shared.event.ErrorLogEvent;
import hayashi.userservice.shared.event.SuccessLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final LogPort logPort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSuccessLog(SuccessLogEvent event) {
        log.info("[handleSuccessLog]: request save log {}", event.getData());
        saveLog(event.getData(), "handleSuccessLog");
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleErrorLog(ErrorLogEvent event) {
        log.info("[handleErrorLog]: request save log {}", event.getData());
        saveLog(event.getData(), "handleErrorLog");
    }

    private void saveLog(RequestSaveLog request, String prefix) {
        logPort.save(request)
                .doOnSuccess(aVoid -> log.info("[{}]: success to save log", prefix))
                .doOnError(throwable -> log.error("[{}]: fail to save log : ", prefix, throwable))
                .onErrorComplete()
                .subscribe();
    }

}
