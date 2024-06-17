package modsen.interns.pizza_modsen.person.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePersonDTO {
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
}
