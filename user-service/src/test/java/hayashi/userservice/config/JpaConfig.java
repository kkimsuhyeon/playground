package hayashi.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("SYSTEM");
    }

}
