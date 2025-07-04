package hayashi.userservice.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestSaveLog {

    private String userId;

    private String requestUri;

    private String requestMethod;

    private Object requestData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    private Object response;

}
