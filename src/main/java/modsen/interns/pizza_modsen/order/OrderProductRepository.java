package modsen.interns.pizza_modsen.order;


import modsen.interns.pizza_modsen.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
    List<OrderProduct> findAllByOrderId(Long id);
}