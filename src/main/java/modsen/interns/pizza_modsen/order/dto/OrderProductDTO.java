package modsen.interns.pizza_modsen.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProductDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private int productQuantity;
}