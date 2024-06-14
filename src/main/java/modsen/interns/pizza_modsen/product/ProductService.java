package modsen.interns.pizza_modsen.product;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.model.Product;
import modsen.interns.pizza_modsen.product.dto.CreateProductDTO;
import modsen.interns.pizza_modsen.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::new);
    }

    public ProductDTO createProduct(CreateProductDTO productDTO) {
        //TODO create constructor Product(CreateProductDTO)
        Product product = new Product();
                //TODO  product.setCategory();
        product.setPrice(product.getPrice());
        product.setDescription(product.getDescription());
        product.setImageUrl(product.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);
    }
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId).get();
                //TODO  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        //todo
        /*
        if (productDTO.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(productDTO.getCategoryId());
            if (category == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + productDTO.getCategoryId());
            }
            existingProduct.setCategory(category);
        }*/
        if (productDTO.getPrice() != 0) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDescription() != null) {
            existingProduct.setDescription(productDTO.getDescription());
        }
        if (productDTO.getImageUrl() != null) {
            existingProduct.setImageUrl(productDTO.getImageUrl());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return new ProductDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}