package modsen.interns.pizza_modsen.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderProductDTO {
    private Long product;
    private int productQuantity;
}
