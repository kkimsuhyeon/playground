package hayashi.userservice.config.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    @Value("${jwt.expiration}")
    private String expiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {

        log.info("secretKey: {}", secretKey);

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

    public boolean tokenValidation(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new IllegalArgumentException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new IllegalArgumentException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("JWT claims string is empty", e);
        }
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

