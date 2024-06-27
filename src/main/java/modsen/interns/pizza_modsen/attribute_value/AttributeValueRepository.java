package modsen.interns.pizza_modsen.attribute_value;

import modsen.interns.pizza_modsen.model.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
}
