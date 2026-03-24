package net.ictcampus.baemtli.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.ictcampus.baemtli.security.SecurityConstants;
import net.ictcampus.baemtli.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Load JWT Secret from application.properties
    @Value("${baemtli.jwt.secret}")
    private String secret;

    // Tokeninformationen abpacken und generieren
    public String generateToken(UserDetails userDetails) {
        User user = (User) userDetails;

        // Fill Information into JWT-Token
        return Jwts.builder()
                .claim("id", user.getId())
                .subject(user.getUsername()) // Nur username, kein Passwort in den JWT schreiben!
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                // Bei Michi: .signWith(getSignInKey(), SignatureAlgorithm.ES256)
                .signWith(getSignInKey())
                .compact();
    }

    public long getExpirationTime() {
        return SecurityConstants.EXPIRATION_TIME;
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Claims müssen wir nicht detailliert erklären können
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Standard Funktion für JWT Claims
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid or expired JWT token", e);
        }
    }
}
