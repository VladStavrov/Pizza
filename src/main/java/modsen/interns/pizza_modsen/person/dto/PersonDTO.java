package modsen.interns.pizza_modsen.person.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.model.Product;
import modsen.interns.pizza_modsen.model.enums.Gender;
import modsen.interns.pizza_modsen.model.enums.UserRole;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonDTO {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private UserRole role;
    private String phoneNumber;
}
