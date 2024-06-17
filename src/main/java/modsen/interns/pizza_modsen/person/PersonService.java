package modsen.interns.pizza_modsen.person;

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
public class PersonService {

    PersonRepository personRepository;
    ModelMapper modelMapper;


    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<PersonDTO> getPersonById(Long id) {
        return personRepository.findById(id)
                .map(PersonDTO::new);
    }

    public PersonDTO createPerson(CreatePersonDTO personDTO) {
        Person person = new Person();
        person.setEmail(personDTO.getEmail());
        person.setUsername(personDTO.getUsername());
        person.setPassword(personDTO.getPassword());
        person.setFullName(personDTO.getFullName());
        person.setPhoneNumber(personDTO.getPhoneNumber());

        Person savedPerson = personRepository.save(person);
        return new PersonDTO(savedPerson);
    }

    public PersonDTO updatePerson(Long personId, PersonDTO personDTO) {
        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id: " + personId));

        if (personDTO.getEmail() != null) {
            existingPerson.setEmail(personDTO.getEmail());
        }
        if (personDTO.getUsername() != null) {
            existingPerson.setUsername(personDTO.getUsername());
        }
        if (personDTO.getPassword() != null) {
            existingPerson.setPassword(personDTO.getPassword());
        }
        if (personDTO.getFullName() != null) {
            existingPerson.setFullName(personDTO.getFullName());
        }
        if (personDTO.getGender() != null) {
            existingPerson.setGender(personDTO.getGender());
        }
        if (personDTO.getBirthDate() != null) {
            existingPerson.setBirthDate(personDTO.getBirthDate());
        }
        if (personDTO.getRole() != null) {
            existingPerson.setRole(personDTO.getRole());
        }
        if (personDTO.getPhoneNumber() != null) {
            existingPerson.setPhoneNumber(personDTO.getPhoneNumber());
        }

        Person updatedPerson = personRepository.save(existingPerson);
        return new PersonDTO(updatedPerson);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }


}
