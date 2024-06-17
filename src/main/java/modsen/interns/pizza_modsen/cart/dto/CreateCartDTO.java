package modsen.interns.pizza_modsen.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCartDTO {
    private Long userId;
    private Long productId;
    private int productQuantity;
}
