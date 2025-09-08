package hayashi.userservice.shared.event;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ErrorLogEvent extends ApplicationEvent {

    private final RequestSaveLog data;

    public ErrorLogEvent(Object source, RequestSaveLog data) {
        super(source);
        this.data = data;
    }
}
