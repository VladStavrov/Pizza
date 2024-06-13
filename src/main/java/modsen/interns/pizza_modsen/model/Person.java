package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private LocalDate birthDate;
    private String role;
    private String phoneNumber;
}