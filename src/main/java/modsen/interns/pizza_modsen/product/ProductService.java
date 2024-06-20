package modsen.interns.pizza_modsen.product;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.category.CategoryService;
import modsen.interns.pizza_modsen.model.Category;
import modsen.interns.pizza_modsen.model.Product;
import modsen.interns.pizza_modsen.product.dto.CreateProductDTO;
import modsen.interns.pizza_modsen.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }
    public ProductDTO createProduct(CreateProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        modelMapper.map(productDTO, existingProduct);
        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        existingProduct.setCategory(category);
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
    private Product convertToEntity(Object productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}