package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartDTO;
import modsen.interns.pizza_modsen.model.Cart;
import modsen.interns.pizza_modsen.model.Category;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.model.Product;
import modsen.interns.pizza_modsen.person.PersonService;
import modsen.interns.pizza_modsen.product.ProductService;
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
public class CartService {

    private final CartRepository cartRepository;
    private final PersonService personService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CartDTO> getCartDTOById(Long id) {
        return cartRepository.findById(id)
                .map(this::convertToDTO);
    }
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with id: " + id));
    }
    public CartDTO createCart(CreateCartDTO createCartDTO) {
        Cart existingCart = convertToEntity(createCartDTO);
        Person person = personService.getPersonByUsername(createCartDTO.getUsername());
        Product product = productService.getProductById(createCartDTO.getProductId());
        existingCart.setUser(person);
        existingCart.setProduct(product);
        Cart updatedCart = cartRepository.save(existingCart);
        return convertToDTO(updatedCart);
    }
    public CartDTO updateCart(Long cartId, CreateCartDTO createCartDTO) {
        Cart existingCart = getCartById(cartId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(createCartDTO,existingCart);
        Person person = personService.getPersonByUsername(createCartDTO.getUsername());
        Product product = productService.getProductById(createCartDTO.getProductId());
        existingCart.setUser(person);
        existingCart.setProduct(product);
        Cart updatedCart = cartRepository.save(existingCart);
        return convertToDTO(updatedCart);
    }
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
    private CartDTO convertToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
    private Cart convertToEntity(Object cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }
}
