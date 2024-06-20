package modsen.interns.pizza_modsen.security;


import jakarta.security.auth.message.AuthException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import modsen.interns.pizza_modsen.person.PersonService;
import modsen.interns.pizza_modsen.security.jwt.TokenUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    TokenUtils tokenUtils;

    PersonService personService;

    PasswordEncoder passwordEncoder;

    public String authenticate(String email, String password) throws AuthException {
        return Optional.of(personService.getPersonByEmail(email))
                .map(person -> {
//                    if (!person.isEnabled()) {
//                        return new AuthException("Account disabled", "PROSELYTE_USER_ACCOUNT_DISABLED");
//                    }

                    if (!passwordEncoder.matches(password, person.get().getPassword())) {
                        return new AuthException("Invalid password");
                    }

                    return tokenUtils.generateToken(person.get());
                })
                .orElseThrow(() -> new AuthException("Invalid username")).toString();
    }
}
