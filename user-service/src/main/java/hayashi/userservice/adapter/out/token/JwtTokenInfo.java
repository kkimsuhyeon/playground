package hayashi.userservice.adapter.out.token;

import lombok.Value;

import java.util.Date;

@Value(staticConstructor = "of")
public class JwtTokenInfo {
    String token;
    Date expiredAt;
}
