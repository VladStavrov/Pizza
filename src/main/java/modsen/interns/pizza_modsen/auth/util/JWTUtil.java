package modsen.interns.pizza_modsen.auth.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import modsen.interns.pizza_modsen.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final SignatureAlgorithm ALGORITHM = HS512;

    @Value("${jwt.lifetime}")
    private Long lifetime; //hours

    public String generateToken(Person user) throws ServletException {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole());
            Date issuedDate = new Date();
            Date expiredDate = new Date(
                    issuedDate.getTime() + Duration.ofHours(lifetime).toMillis()
            );
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setIssuedAt(issuedDate)
                    .setExpiration(expiredDate)
                    .signWith(ALGORITHM, secret)
                    .compact();
        } catch (Exception e) {
            throw new ServletException("Error creating JWT token", e);
        }
    }

    public String getUsernameFromJwt(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRoleFromJwt(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
