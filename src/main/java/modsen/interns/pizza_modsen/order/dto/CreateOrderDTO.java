package modsen.interns.pizza_modsen.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.enums.OrderStatus;
import modsen.interns.pizza_modsen.model.enums.OrderType;
import modsen.interns.pizza_modsen.model.enums.PaymentMethod;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateOrderDTO {
    private String orderAddress;
    private OrderStatus orderStatus;
    private double orderTotal;
    private OrderType orderType;
    private PaymentMethod paymentMethod;
}
