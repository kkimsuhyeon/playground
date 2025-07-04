package hayashi.logservice.application.factory;

import hayashi.logservice.application.command.SaveLogCommand;
import hayashi.logservice.domain.model.LogDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFactory {

    public LogDocument toDocument(SaveLogCommand command) {
        return LogDocument.builder()
                .userId(command.getUserId())
                .requestUri(command.getRequestUri())
                .requestMethod(command.getRequestMethod())
                .requestData(command.getRequestData())
                .requestAt(command.getRequestAt())
                .response(command.getResponse())
                .build();
    }
}
