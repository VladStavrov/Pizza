package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartDTO;
import modsen.interns.pizza_modsen.model.Cart;
import modsen.interns.pizza_modsen.person.PersonRepository;
import modsen.interns.pizza_modsen.product.ProductRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CartDTO> getCartById(Long id) {
        return cartRepository.findById(id)
                .map(this::convertToDTO);
    }

    public CartDTO createCart(CreateCartDTO createCartDTO) {
        Cart cart = new Cart();
        return getCartFromDTO(createCartDTO, cart);
    }

    public CartDTO updateCart(Long cartId, CreateCartDTO createCartDTO) {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with id: " + cartId));

        return getCartFromDTO(createCartDTO, existingCart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO convertToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }

    private Cart convertToEntity(CreateCartDTO createCartDTO) {
        return modelMapper.map(createCartDTO, Cart.class);
    }

    private CartDTO getCartFromDTO(CreateCartDTO createCartDTO, Cart existingCart) {
        existingCart.setUser(personRepository.findById(createCartDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + createCartDTO.getUserId())));
        existingCart.setProduct(productRepository.findById(createCartDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + createCartDTO.getProductId())));
        existingCart.setProductQuantity(createCartDTO.getProductQuantity());

        Cart updatedCart = cartRepository.save(existingCart);
        return convertToDTO(updatedCart);
    }
}
