package modsen.interns.pizza_modsen.person;

import modsen.interns.pizza_modsen.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
