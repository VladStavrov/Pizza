package modsen.interns.pizza_modsen.auth.dto;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}
