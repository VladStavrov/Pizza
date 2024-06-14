package modsen.interns.pizza_modsen.category;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.dto.CategoryDTO;
import modsen.interns.pizza_modsen.category.dto.CreateCategoryDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(CategoryController.BASE_URL)
@RequiredArgsConstructor
public class CategoryController {
    public static final String BASE_URL = "/categories";

    public static final String ID_PATH = "/{id}";

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories(){return categoryService.getAllCategories();}

    @GetMapping(ID_PATH)
    public Optional<CategoryDTO> getCategoryById(@PathVariable Long id){return categoryService.getCategoryById(id);}

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CreateCategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping(ID_PATH)
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        categoryDTO.setId(id);
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping(ID_PATH)
    public void deleteCategory(@PathVariable Long id){categoryService.deleteCategory(id);}
}
