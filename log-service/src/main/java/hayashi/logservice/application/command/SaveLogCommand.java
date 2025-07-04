package hayashi.logservice.application.command;

import lombok.Value;

@Value(staticConstructor = "of")
public class SaveLogCommand {
    String userId;

    String requestUri;

    String requestMethod;

    Object requestData;

    String requestAt;

    Object response;
}
