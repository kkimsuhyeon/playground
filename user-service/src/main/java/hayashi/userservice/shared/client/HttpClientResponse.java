package hayashi.userservice.shared.client;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class HttpClientResponse<T> {
    private HttpStatus status;
    private HttpHeaders headers;
    private T body;
    private String rawBody;
}
