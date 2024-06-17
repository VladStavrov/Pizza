package modsen.interns.pizza_modsen.person;


import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.person.dto.CreatePersonDTO;
import modsen.interns.pizza_modsen.person.dto.PersonDTO;
import modsen.interns.pizza_modsen.product.ProductController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {
    public static final String BASE_URL = "/persons";
    public static final String ID_PATH = "/{id}";

    private final PersonService personService;

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(ID_PATH)
    public Optional<PersonDTO> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody CreatePersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @PutMapping(ID_PATH)
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        personDTO.setId(id);
        return personService.updatePerson(id, personDTO);
    }

    @DeleteMapping(ID_PATH)
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

}
