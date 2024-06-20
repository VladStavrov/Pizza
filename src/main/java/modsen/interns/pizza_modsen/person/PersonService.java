package modsen.interns.pizza_modsen.person;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.CategoryService;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.person.dto.CreatePersonDTO;
import modsen.interns.pizza_modsen.person.dto.PersonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final ModelMapper modelMapper;

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonDTO> getPersonDTOByUsername(String username) {
        return personRepository.findByUsername(username)
                .map(this::convertToDTO);
    }
    public Person getPersonByUsername(String username){
        return personRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with username: " + username));
    }

    public Optional<PersonDTO> getPersonByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    public PersonDTO createPerson(CreatePersonDTO createPersonDTO) {
        Person person = convertToEntity(createPersonDTO);

        Person savedPerson = personRepository.save(person);
        return convertToDTO(savedPerson);
    }

    public PersonDTO updatePerson(String username, CreatePersonDTO personDTO) {
        Person existingPerson = getPersonByUsername(username);

        modelMapper.map(personDTO, existingPerson);
        Person updatedPerson = personRepository.save(existingPerson);
        return convertToDTO(updatedPerson);
    }

    public void deletePerson(String username) {
        personRepository.deleteByUsername(username);
    }

    private PersonDTO convertToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person convertToEntity(Object personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
