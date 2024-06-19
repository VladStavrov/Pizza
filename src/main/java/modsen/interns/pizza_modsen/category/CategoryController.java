package modsen.interns.pizza_modsen.category;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.dto.CategoryDTO;
import modsen.interns.pizza_modsen.category.dto.CreateCategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(id);
        return categoryDTO.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}