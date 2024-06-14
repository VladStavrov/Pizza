package modsen.interns.pizza_modsen.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Category;

@Data
@NoArgsConstructor
public class CategoryDTO {

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    private Long id;

    private String name;

}
