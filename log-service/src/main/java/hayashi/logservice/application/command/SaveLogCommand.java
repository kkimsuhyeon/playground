package hayashi.logservice.application.command;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value(staticConstructor = "of")
public class SaveLogCommand {
    String userId;
    Map<String, Object> request;
    Map<String, Object> response;
    LocalDateTime requestAt;
}
