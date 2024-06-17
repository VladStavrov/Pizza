package modsen.interns.pizza_modsen.person.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.model.Product;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonDTO {
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.username = person.getUsername();
        this.password = person.getPassword();
        this.fullName = person.getFullName();
        this.gender = person.getGender();
        this.birthDate = person.getBirthDate();
        this.role = person.getRole();
        this.phoneNumber = person.getPhoneNumber();
    }
    private Long id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String role;
    private String phoneNumber;
}
