package modsen.interns.pizza_modsen.product.dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import modsen.interns.pizza_modsen.model.Order;
import modsen.interns.pizza_modsen.model.Product;

import java.util.stream.Collectors;
@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private Long categoryId;
    private double price;
    private String description;
    private String imageUrl;
}