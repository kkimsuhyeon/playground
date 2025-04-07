package hayashi.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final ObjectMapper objectMapper;

    public AuthenticationFilter(ObjectMapper objectMapper) {
        super(Config.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }


    public static class Config {
    }

}
