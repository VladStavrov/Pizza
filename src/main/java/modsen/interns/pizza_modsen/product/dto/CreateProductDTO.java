package modsen.interns.pizza_modsen.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Product;

@Data
@NoArgsConstructor
public class CreateProductDTO {
    private double price;
    private String description;
    private String imageUrl;
    private Long categoryId;
}
