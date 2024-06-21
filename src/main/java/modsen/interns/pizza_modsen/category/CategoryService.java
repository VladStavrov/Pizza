package modsen.interns.pizza_modsen.category;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.dto.CategoryDTO;
import modsen.interns.pizza_modsen.category.dto.CreateCategoryDTO;
import modsen.interns.pizza_modsen.model.Category;
import modsen.interns.pizza_modsen.model.Order;
import modsen.interns.pizza_modsen.order.dto.OrderDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryDTOById(Long id){
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }
    public Category getCategoryById(Long id){
       return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));
    }

    public CategoryDTO createCategory(CreateCategoryDTO categoryDTO){
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);

    }

    public CategoryDTO updateCategory(Long categoryId, CreateCategoryDTO categoryDTO){
        Category existingCategory = getCategoryById(categoryId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(categoryDTO,existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    public void deleteCategory(Long id){categoryRepository.deleteById(id);}
    private CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category convertToEntity(Object categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
