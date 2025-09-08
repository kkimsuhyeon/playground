package hayashi.userservice.shared.client;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Getter
@Builder(toBuilder = true)
public class HttpClientRequest<T> {

    @NotNull
    private HttpMethod method;

    @Builder.Default
    private HttpHeaders headers = new HttpHeaders();

    @NotNull
    private String uri;

    private T body;

    public HttpClientRequest<T> addHeader(String name, String value) {
        if (this.headers == null) this.headers = new HttpHeaders();

        this.headers.add(name, value);
        return this;
    }
}
