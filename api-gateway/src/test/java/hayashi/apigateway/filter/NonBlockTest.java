package hayashi.apigateway.filter;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class NonBlockTest {

    @Test
    public void nonBlockTest() {
        Mono<String> test = Mono.just("test");
        System.out.println(test.subscribe());
    }
}
