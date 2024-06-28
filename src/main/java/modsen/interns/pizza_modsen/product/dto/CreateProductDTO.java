package modsen.interns.pizza_modsen.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProductDTO {
    private double price;
    private String title;
    private String description;
    private String imageUrl;
    private Long category;
}
