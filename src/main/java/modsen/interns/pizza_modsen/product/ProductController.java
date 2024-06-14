package modsen.interns.pizza_modsen.product;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.product.dto.CreateProductDTO;
import modsen.interns.pizza_modsen.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
public class ProductController {

    public static final String BASE_URL = "/products";
    public static final String ID_PATH = "/{id}";

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(ID_PATH)
    public Optional<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody CreateProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping(ID_PATH)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        return productService.updateProduct(id,productDTO);
    }

    @DeleteMapping(ID_PATH)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}