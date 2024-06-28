package modsen.interns.pizza_modsen.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Cart;
import modsen.interns.pizza_modsen.model.CartProduct;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private String username;
    private List<CartProductDTO> cartProductDTOList;
}