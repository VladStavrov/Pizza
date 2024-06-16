package modsen.interns.pizza_modsen.cart.service;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartDTO;
import modsen.interns.pizza_modsen.model.Cart;
import modsen.interns.pizza_modsen.cart.CartRepository;
import modsen.interns.pizza_modsen.repository.PersonRepository;
import modsen.interns.pizza_modsen.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CartDTO> getCartById(Long id) {
        return cartRepository.findById(id)
                .map(CartDTO::new);
    }

    public CartDTO createCart(CreateCartDTO createCartDTO) {
        Cart cart = new Cart();
        cart.setUser(personRepository.findById(createCartDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + createCartDTO.getUserId())));
        cart.setProduct(productRepository.findById(createCartDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + createCartDTO.getProductId())));
        cart.setProductQuantity(createCartDTO.getProductQuantity());

        Cart savedCart = cartRepository.save(cart);
        return new CartDTO(savedCart);
    }

    public CartDTO updateCart(Long cartId, CreateCartDTO createCartDTO) {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with id: " + cartId));

        existingCart.setUser(personRepository.findById(createCartDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + createCartDTO.getUserId())));
        existingCart.setProduct(productRepository.findById(createCartDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + createCartDTO.getProductId())));
        existingCart.setProductQuantity(createCartDTO.getProductQuantity());

        Cart updatedCart = cartRepository.save(existingCart);
        return new CartDTO(updatedCart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
