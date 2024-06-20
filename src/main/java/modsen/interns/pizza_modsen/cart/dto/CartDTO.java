package modsen.interns.pizza_modsen.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Cart;

@Data
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private String username;
    private Long productId;
    private int productQuantity;


}