package modsen.interns.pizza_modsen.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenServerAuthenticationConverter implements AuthenticationConverter {

    private final JwtHandler jwtHandler;


    private static final String BEARER_PREFIX = "Bearer ";
    private static final Function<String, String> getBearerValue = authValue -> authValue.substring(BEARER_PREFIX.length());

    @Override
    public Authentication convert(HttpServletRequest request) {
        return Optional.of(request)
                .map(this::extractHeader)
                .map(getBearerValue)
                .map(jwtHandler::check)
                .map(UserAuthenticationBearer::create)
                .orElse(null);
    }

    private String extractHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
