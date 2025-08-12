package hayashi.userservice.shared.event.listener;

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

        logPort.save(event.getData())
                .doOnSuccess(aVoid -> log.info("[handleSuccessLog]: success to save log"))
                .doOnError(throwable -> log.error("[handleSuccessLog]: fail to save log : ", throwable))
                .onErrorComplete()
                .subscribe();
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleErrorLog(ErrorLogEvent event) {
        log.info("[handleErrorLog]: request save log {}", event.getData());

        logPort.save(event.getData())
                .doOnSuccess(aVoid -> log.info("[handleErrorLog]: success to save log"))
                .doOnError(throwable -> log.error("[handleErrorLog]: fail to save log : ", throwable))
                .onErrorComplete()
                .subscribe();
    }

}
