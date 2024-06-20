package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(CartController.BASE_URL)
@RequiredArgsConstructor
public class CartController {

    public static final String BASE_URL = "/carts";
    public static final String ID_PATH = "/{id}";

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Optional<CartDTO> cartDTO = cartService.getCartDTOById(id);
        return cartDTO.map(cart -> new ResponseEntity<>(cart, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody CreateCartDTO createCartDTO) {
        CartDTO createdCart = cartService.createCart(createCartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody CreateCartDTO createCartDTO) {
        CartDTO updatedCart = cartService.updateCart(id, createCartDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
