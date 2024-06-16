package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartDTO;
import modsen.interns.pizza_modsen.cart.service.CartService;
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
    public List<CartDTO> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping(ID_PATH)
    public Optional<CartDTO> getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping
    public CartDTO createCart(@RequestBody CreateCartDTO createCartDTO) {
        return cartService.createCart(createCartDTO);
    }

    @PutMapping(ID_PATH)
    public CartDTO updateCart(@PathVariable Long id, @RequestBody CreateCartDTO createCartDTO) {
        return cartService.updateCart(id, createCartDTO);
    }

    @DeleteMapping(ID_PATH)
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }
}
