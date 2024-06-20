package modsen.interns.pizza_modsen.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
}
