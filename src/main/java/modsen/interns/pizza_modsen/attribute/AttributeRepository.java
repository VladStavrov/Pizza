package modsen.interns.pizza_modsen.attribute;

import modsen.interns.pizza_modsen.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
