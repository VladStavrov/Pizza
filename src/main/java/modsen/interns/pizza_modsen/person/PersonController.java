package modsen.interns.pizza_modsen.person;


import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.person.dto.CreatePersonDTO;
import modsen.interns.pizza_modsen.person.dto.PersonDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PersonController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {

    public static final String BASE_URL = "/persons";
    public static final String USERNAME_PATH = "/{username}";

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(USERNAME_PATH)
    public ResponseEntity<?>  getPersonById(@PathVariable String username) {
        Optional<PersonDTO> personDTO = personService.getPersonDTOByUsername(username);
        return personDTO.map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?>  createPerson(@RequestBody CreatePersonDTO personDTO) {
        PersonDTO createdPerson = personService.createPerson(personDTO);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping(USERNAME_PATH)
    public ResponseEntity<?>  updatePerson(@PathVariable String username, @RequestBody CreatePersonDTO personDTO) {

        PersonDTO updatedPerson = personService.updatePerson(username, personDTO);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping(USERNAME_PATH)
    public ResponseEntity<?>  deletePerson(@PathVariable String username) {
        personService.deletePerson(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
