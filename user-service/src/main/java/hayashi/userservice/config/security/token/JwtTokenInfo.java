package hayashi.userservice.config.security.token;

import lombok.Value;

import java.util.Date;

@Value(staticConstructor = "of")
public class JwtTokenInfo {
    String token;
    Date expiredAt;
}
