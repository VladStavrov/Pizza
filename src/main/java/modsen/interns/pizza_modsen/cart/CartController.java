package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CartProductDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        Optional<CartDTO> cartDTO = cartService.getCartDTOById(id);
        return cartDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CartDTO> getCartByUsername(@PathVariable String username) {
        CartDTO cartDTO = cartService.getCartDTOByUsername(username);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CartProductDTO> getCartProductById(@PathVariable Long id) {
        Optional<CartProductDTO> cartProductDTO = cartService.getCartProductDTOById(id);
        return cartProductDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/username/{username}/products")
    public ResponseEntity<CartProductDTO> addProductToCart(@PathVariable String username,
                                                           @RequestBody CreateCartProductDTO createCartProductDTO) {
        CartProductDTO cartProductDTO = cartService.addProductToCart(createCartProductDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartProductDTO);
    }

    @PutMapping("/products/{cartId}")
    public ResponseEntity<CartProductDTO> updateCartProduct(@PathVariable Long cartId,
                                                            @RequestBody CreateCartProductDTO createCartProductDTO) {
        CartProductDTO cartProductDTO = cartService.updateCartProduct(cartId, createCartProductDTO);
        return ResponseEntity.ok(cartProductDTO);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartService.deleteCartProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
