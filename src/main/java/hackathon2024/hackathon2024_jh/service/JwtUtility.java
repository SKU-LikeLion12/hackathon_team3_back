package hackathon2024.hackathon2024_jh.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtUtility {
    private String secret = "yoursecretkey";

    private static final long EXPIRATION_TIME = 1000L * 60 * 60;

    public String generateJwtToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch(Exception ex) {
            throw ex;
        }
    }
}
