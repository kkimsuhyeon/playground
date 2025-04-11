package hayashi.apigateway.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

class FilterTest {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Test
    void matcherTest() {
        String passApi = "/api/v.*/users/login";
        String requestApi = "/api/v1/users/login";

        Assertions.assertThat(requestApi).matches(passApi);
    }

    @Test
    void pathMatcherTest() {
        String passApi = "/api/v*/**/login";
        String requestApi = "/api/v1/users/login";

        Assertions.assertThat(pathMatcher.match(passApi, requestApi)).isTrue();
    }
}
