package modsen.interns.pizza_modsen.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TestAuthController {

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Insecured data ";
    }

    @GetMapping("/secured")
    public String securedData(Principal principal) {
        return "Secured data" + principal.getName();
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public String UserData(Principal principal) {
        return principal.getName();
    }
}
