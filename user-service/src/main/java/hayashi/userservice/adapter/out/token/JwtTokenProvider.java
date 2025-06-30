package hayashi.userservice.adapter.out.token;

import io.jsonwebtoken.Claims;
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

    public JwtTokenInfo createToken(JwtTokenPayload payload) {
        Date now = new Date();
        Date expiredDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

        String token = Jwts.builder()
                .signWith(secretKey)
                .subject(payload.getId())
                .claims(payload.toMap())
                .issuedAt(now)
                .expiration(expiredDate)
                .compact();

        return JwtTokenInfo.of(token, expiredDate);
    }

    public JwtTokenPayload getPayload(String token) {
        Claims claims = parseToken(token);
        return JwtTokenPayload.from(claims);
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

