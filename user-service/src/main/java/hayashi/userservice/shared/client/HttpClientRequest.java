package hayashi.userservice.shared.client;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Getter
@Builder(toBuilder = true)
public class HttpClientRequest<T> {
    private HttpMethod method;
    private HttpHeaders headers;
    private String uri;
    private T body;
}
