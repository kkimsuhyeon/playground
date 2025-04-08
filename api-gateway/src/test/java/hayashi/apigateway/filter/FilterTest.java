package hayashi.apigateway.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilterTest {

    @Test
    public void testFilter() {
        String passApi = "/api/v.*/users/login";
        String requestApi = "/api/v1/users/login";

        Assertions.assertThat(requestApi.matches(passApi)).isTrue();
    }
}
