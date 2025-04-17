package hayashi.userservice.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.expiration}")
    private String expiration;

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken() {
        Date now = new Date();
        Date expiredDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

        return Jwts.builder()
                .signWith(secretKey)
                .issuedAt(now)
                .expiration(expiredDate)
                .compact();
    }
}

