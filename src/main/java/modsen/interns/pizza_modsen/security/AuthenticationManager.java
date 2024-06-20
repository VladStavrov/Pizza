package modsen.interns.pizza_modsen.security;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.person.PersonService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    PersonService personService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return Optional.of(personService.getPersonById(principal.getId()))
//                .filter(Person::isEnabled)
                .map(user -> authentication)
                .orElseThrow(() -> new RuntimeException("User disabled"));
    }
}