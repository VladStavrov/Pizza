package modsen.interns.pizza_modsen.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Cart;

@Data
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private int productQuantity;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.productId = cart.getProduct().getId();
        this.productQuantity = cart.getProductQuantity();
    }
}