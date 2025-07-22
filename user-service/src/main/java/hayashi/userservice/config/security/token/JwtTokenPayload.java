package hayashi.userservice.config.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hayashi.userservice.domain.model.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(staticName = "of")
public class JwtTokenPayload {

    private String id;
    private String name;
    private String email;
    private List<String> authorities;

    public static JwtTokenPayload from(UserEntity user) {
        return JwtTokenPayload.of(user.getId(), user.getName(), user.getEmail(), List.of("USER"));
    }

    public static JwtTokenPayload from(Claims claims) {
        return JwtTokenPayload.of(
                claims.get("id", String.class),
                claims.get("name", String.class),
                claims.get("email", String.class),
                claims.get("authorities", List.class)
        );
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "name", name,
                "email", email,
                "authorities", authorities
        );
    }

    @JsonIgnore
    public List<GrantedAuthority> getGrantedAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
