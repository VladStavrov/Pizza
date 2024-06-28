package modsen.interns.pizza_modsen.cart;

import modsen.interns.pizza_modsen.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct,Long> {

    List<CartProduct> findAllByCart_Id(Long id);
}
