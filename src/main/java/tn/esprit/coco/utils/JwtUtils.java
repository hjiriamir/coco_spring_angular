package tn.esprit.coco.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tn.esprit.coco.service.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${coco.app.jwtSecret}")
    private String jwtSecret;

    @Value("${coco.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            return Jwts.builder()
                    .setSubject((userPrincipal.getEmail())) // Assuming getEmail() exists in UserDetailsImpl
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact();
        } else {
            throw new IllegalArgumentException("Authentication principal is not an instance of UserDetailsImpl");
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
