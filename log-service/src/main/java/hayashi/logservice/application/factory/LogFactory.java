package hayashi.logservice.application.factory;

import hayashi.logservice.application.command.SaveLogCommand;
import hayashi.logservice.domain.model.LogDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LogFactory {

    public LogDocument toDocument(SaveLogCommand command) {
        return LogDocument.builder()
                .userId(command.getUserId())
                .request(command.getRequest())
                .response(command.getResponse())
                .requestAt(command.getRequestAt())
                .build();
    }
}
