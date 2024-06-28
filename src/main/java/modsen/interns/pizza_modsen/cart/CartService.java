package modsen.interns.pizza_modsen.cart;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.cart.dto.CartDTO;
import modsen.interns.pizza_modsen.cart.dto.CartProductDTO;
import modsen.interns.pizza_modsen.cart.dto.CreateCartProductDTO;
import modsen.interns.pizza_modsen.model.*;
import modsen.interns.pizza_modsen.person.PersonService;
import modsen.interns.pizza_modsen.product.ProductService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
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

    public Optional<CartProductDTO> getCartProductDTOById(Long id) {
        return cartProductRepository.findById(id)
                .map(this::convertToProductDTO);
    }

    public CartProduct getCartProductById(Long id) {
        return cartProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CartProduct not found with id: " + id));
    }

    @Transactional
    public Cart getCartByUsername(String username) {
        return cartRepository.findByUserUsername(username).orElseGet(() -> {
            Person person = personService.getPersonByUsername(username);
            Cart cart = new Cart();
            cart.setUser(person);
            return cartRepository.save(cart);
        });
    }

    public CartDTO getCartDTOByUsername(String username) {
        Cart cart = getCartByUsername(username);
        CartDTO cartDTO = convertToDTO(cart);
        cartDTO.setUsername(username);
        List<CartProductDTO> cartProducts = cartProductRepository.findAllByCart_Id(cart.getId())
                .stream().map(this::convertToProductDTO).collect(Collectors.toList());
        cartDTO.setCartProductDTOList(cartProducts);
        return cartDTO;
    }

    @Transactional
    public CartProductDTO addProductToCart(CreateCartProductDTO createCartDTO, String username) {
        Cart cart = getCartByUsername(username);
        Product product = productService.getProductById(createCartDTO.getProduct());
        CartProduct cartProduct = convertToProductEntity(createCartDTO);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);
        return convertToProductDTO(savedCartProduct);
    }

    @Transactional
    public CartProductDTO updateCartProduct(Long cartId, CreateCartProductDTO createCartDTO) {
        CartProduct existingCartProduct = getCartProductById(cartId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(createCartDTO, existingCartProduct);
        CartProduct updatedCartProduct = cartProductRepository.save(existingCartProduct);
        return convertToProductDTO(updatedCartProduct);
    }

    @Transactional
    public void deleteCartProduct(Long id) {
        if (!cartProductRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartProduct not found with id: " + id);
        }
        cartProductRepository.deleteById(id);
    }

    private CartDTO convertToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }

    private Cart convertToEntity(Object cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    private CartProductDTO convertToProductDTO(CartProduct cartProduct) {
        return modelMapper.map(cartProduct, CartProductDTO.class);
    }

    private CartProduct convertToProductEntity(Object cartProductDTO) {
        return modelMapper.map(cartProductDTO, CartProduct.class);
    }
}