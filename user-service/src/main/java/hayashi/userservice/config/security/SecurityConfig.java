package hayashi.userservice.config.security;

import hayashi.userservice.config.security.filter.AuthUserFilter;
import hayashi.userservice.config.security.filter.TokenVerificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final List<String> ALLOWED_OPTION_URI = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/health-check",
            "/resource/**",
            "/user-service/swagger-ui/**",
            "/user-service/v3/api-docs/**",
            "/actuator/**"
    );

    public static final List<String> ALLOWED_BUSINESS_URI = List.of(
            "/api/v1/users/token/**",
            "/api/v1/users/join",
            "/api/v1/users/login"
    );

    private final TokenVerificationFilter tokenVerificationFilter;
    private final AuthUserFilter authUserFilter;

    private final SecurityAccessDeniedHandler securityAccessDeniedHandler;
    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    public SecurityConfig(@Autowired(required = false) TokenVerificationFilter tokenVerificationFilter,
                          AuthUserFilter authUserFilter,
                          SecurityAccessDeniedHandler securityAccessDeniedHandler,
                          SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint) {
        this.tokenVerificationFilter = tokenVerificationFilter;
        this.authUserFilter = authUserFilter;
        this.securityAccessDeniedHandler = securityAccessDeniedHandler;
        this.securityAuthenticationEntryPoint = securityAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSecurity httpSecurity = http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ALLOWED_OPTION_URI.toArray(String[]::new)).permitAll()
                        .requestMatchers(ALLOWED_BUSINESS_URI.toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated());

        if (tokenVerificationFilter != null) {
            httpSecurity.addFilterBefore(tokenVerificationFilter, UsernamePasswordAuthenticationFilter.class);
            httpSecurity.addFilterAfter(authUserFilter, TokenVerificationFilter.class);
        } else {
            httpSecurity.addFilterBefore(authUserFilter, UsernamePasswordAuthenticationFilter.class);
        }

        return httpSecurity.exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(securityAuthenticationEntryPoint)
                        .accessDeniedHandler(securityAccessDeniedHandler)
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
