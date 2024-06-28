package modsen.interns.pizza_modsen.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CreateCartProductDTO;
import modsen.interns.pizza_modsen.model.enums.OrderStatus;
import modsen.interns.pizza_modsen.model.enums.OrderType;
import modsen.interns.pizza_modsen.model.enums.PaymentMethod;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderDTO {
    private String orderAddress;
    private OrderStatus orderStatus;
    private double orderTotal;
    private OrderType orderType;
    private PaymentMethod paymentMethod;
    private List<CreateOrderProductDTO> orderProducts;
}
