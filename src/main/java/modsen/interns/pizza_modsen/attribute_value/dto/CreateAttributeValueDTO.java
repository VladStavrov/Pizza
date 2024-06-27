package modsen.interns.pizza_modsen.attribute_value.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAttributeValueDTO {
    private String value;
    private String slug;
    private Long attributeId;
    private Long productId;
}