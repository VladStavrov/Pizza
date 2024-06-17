package modsen.interns.pizza_modsen.cart;

import modsen.interns.pizza_modsen.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
