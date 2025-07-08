package hayashi.logservice.adapter.in.web.reponse;

import hayashi.logservice.domain.model.LogDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class LogResponse {

    private String id;

    private String userId;

    private String requestAt;

    public static LogResponse from(LogDocument document) {
        return LogResponse.of(document.getId(), document.getUserId(), document.getRequestAt());
    }
}
