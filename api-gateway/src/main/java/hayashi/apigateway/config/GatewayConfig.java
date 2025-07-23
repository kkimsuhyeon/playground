package hayashi.apigateway.config;

import hayashi.apigateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator userRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("user-service-route", r -> r
                        .path("/api/v*/users/**")
                        .filters(f -> f
                                .rewritePath("/api/v*/users/(?<segment>.*)", "/${segment}")
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .removeRequestHeader("Authorization")
                        ).uri("lb://user-service")
                )
                .build();
    }
}
