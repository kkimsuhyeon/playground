package hayashi.userservice.config.security.token;

import hayashi.userservice.domain.model.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(staticName = "of")
public class JwtTokenPayload {

    private String id;
    private String name;
    private String email;

    public static JwtTokenPayload from(UserEntity user) {
        return JwtTokenPayload.of(user.getId(), user.getName(), user.getEmail());
    }

    public static JwtTokenPayload from(Claims claims) {
        return JwtTokenPayload.of(claims.get("id", String.class), claims.get("name", String.class), claims.get("email", String.class));
    }

    public Map<String, Object> toMap() {
        return Map.of("id", id, "name", name, "email", email);
    }
}
