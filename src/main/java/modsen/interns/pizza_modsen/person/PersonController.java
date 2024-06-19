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
    public static final String ID_PATH = "/{id}";

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?>  getPersonById(@PathVariable Long id) {
        Optional<PersonDTO> personDTO = personService.getPersonById(id);
        return personDTO.map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?>  createPerson(@RequestBody CreatePersonDTO personDTO) {
        PersonDTO createdPerson = personService.createPerson(personDTO);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?>  updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        personDTO.setId(id);
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?>  deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
