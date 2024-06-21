package modsen.interns.pizza_modsen.auth;


import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.auth.dto.JwtRequest;
import modsen.interns.pizza_modsen.auth.dto.JwtResponse;
import modsen.interns.pizza_modsen.auth.util.JWTUtil;
import modsen.interns.pizza_modsen.person.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PersonService personService;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


        public JwtResponse authenticate(JwtRequest jwtRequest) {
        return Optional
                .of(personService.getPersonByUsername(jwtRequest.getUsername()))
                .map(user -> {
                    if (!passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect email or password");
                    }
                    try {
                        String accessToken = jwtUtil.generateToken(user);
                        return new JwtResponse(accessToken);
                    } catch (ServletException e) {
                        throw new RuntimeException("Error creating JWT token", e);
                    }
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect email or password"));
    }

}
