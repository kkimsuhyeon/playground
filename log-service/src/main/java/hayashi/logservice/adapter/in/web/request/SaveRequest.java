package hayashi.logservice.adapter.in.web.request;

import hayashi.logservice.application.command.SaveLogCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveRequest {

    @NotBlank
    private String userId;

    private String requestUri;

    private String requestMethod;

    private Object requestData;

    private String requestAt;

    private Object response;

    public SaveLogCommand toCommand() {
        return SaveLogCommand.of(userId, requestUri, requestMethod, requestData, requestAt, response);
    }

}
