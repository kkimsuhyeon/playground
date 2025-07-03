package hayashi.logservice.adapter.in.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import hayashi.logservice.application.command.SaveLogCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Valid
@Data
public class SaveRequest {

    @NotBlank
    private String userId;

    private Map<String, Object> request;

    private Map<String, Object> response;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    public SaveLogCommand toCommand() {
        return SaveLogCommand.of(userId, request, response, requestAt);
    }

}
