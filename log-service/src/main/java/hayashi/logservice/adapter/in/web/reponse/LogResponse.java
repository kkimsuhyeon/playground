package hayashi.logservice.adapter.in.web.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import hayashi.logservice.domain.model.LogDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class LogResponse {

    private String id;
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    public static LogResponse from(LogDocument document) {
        return new LogResponse(document.getId(), document.getUserId(), document.getRequestAt());
    }
}
