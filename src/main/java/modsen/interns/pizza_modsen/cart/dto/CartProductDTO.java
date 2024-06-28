package modsen.interns.pizza_modsen.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartProductDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private int productQuantity;
}
