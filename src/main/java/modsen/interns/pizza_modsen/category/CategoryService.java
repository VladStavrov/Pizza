package modsen.interns.pizza_modsen.category;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.dto.CategoryDTO;
import modsen.interns.pizza_modsen.category.dto.CreateCategoryDTO;
import modsen.interns.pizza_modsen.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryDTOById(Long id){
        return categoryRepository.findById(id)
                .map(CategoryDTO::new);
    }
    public Category getCategoryById(Long id){
       return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));
    }

    public CategoryDTO createCategory(CreateCategoryDTO categoryDTO){
        Category category = new Category();

        category.setName(categoryDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory);

    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO){

        Category existingCategory = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + categoryId));

        if(categoryDTO.getName() != null){
            existingCategory.setName(categoryDTO.getName());
        }

        Category updatedCategory = categoryRepository.save(existingCategory);
        return new CategoryDTO(updatedCategory);
    }

    public void deleteCategory(Long id){categoryRepository.deleteById(id);}

}
