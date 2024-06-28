package modsen.interns.pizza_modsen.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCartProductDTO {
    private Long product;
    private int productQuantity;
}
