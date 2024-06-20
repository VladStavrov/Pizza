package modsen.interns.pizza_modsen.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.person.PersonService;
import modsen.interns.pizza_modsen.person.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenUtils {
    final PersonService personService;
    final PasswordEncoder passwordEncoder;
    final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    @Value("${jwt.secret}")
    String secret;
    @Value("${jwt.expiration}")
    Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    String issuer;


    public String generateToken(PersonDTO person) {
        Map<String, Object> claims = new HashMap<>() {{
            put("role", person.getRole());
            put("email", person.getEmail());
        }};
        return generateToken(claims, person.getId().toString());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        Long expirationTimeInMillis = expirationInSeconds * 1000L;
        Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);

        return generateToken(expirationDate, claims, subject);
    }

    private String generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        Date createdDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(ALGORITHM, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();


    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles",List.class);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
