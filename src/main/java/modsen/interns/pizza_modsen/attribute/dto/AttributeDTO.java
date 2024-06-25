package modsen.interns.pizza_modsen.attribute.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Category;
import java.util.List;

@Data
@NoArgsConstructor
public class AttributeDTO {
    private Long id;
    private String title;
    private String slug;
    private String type;
    private List<Category> categories;
}
