package hayashi.userservice.shared.client;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
@Builder(toBuilder = true)
public class HttpClientResponse<T> {
    private HttpStatus status;
    private HttpHeaders headers;
    private T body;
    private String rawBody;
}
