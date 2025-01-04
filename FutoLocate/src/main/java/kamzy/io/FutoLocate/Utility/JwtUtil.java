package kamzy.io.FutoLocate.Utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kamzy.io.FutoLocate.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "a3DkZ8mP1qJx5vR7wXcT9Ln2oYbH4sF6";// Replace with a secure key
    private static final String ENCODED_SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 hours

    // Generate Token
    public String generateToken(Users userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getEmail());
    }

    // Create Token with claims and subject
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, ENCODED_SECRET_KEY)
                .compact();
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Expiration Date from Token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean validateToken(String token, String email) {
        final String username = extractUsername(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    // Check if Token is Expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Parse Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(ENCODED_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
