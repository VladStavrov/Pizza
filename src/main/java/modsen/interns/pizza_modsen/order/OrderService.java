package modsen.interns.pizza_modsen.order;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.model.Order;
import modsen.interns.pizza_modsen.model.OrderProduct;
import modsen.interns.pizza_modsen.model.Person;
import modsen.interns.pizza_modsen.order.dto.CreateOrderDTO;
import modsen.interns.pizza_modsen.order.dto.OrderDTO;
import modsen.interns.pizza_modsen.order.dto.OrderProductDTO;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PersonService personService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> getOrderDTOById(order.getId()))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderDTOById(Long id) {
        Order order =  getOrderById(id);
        OrderDTO orderDTO = convertToDTO(order);
        List<OrderProductDTO> orderProductDTOS = orderProductRepository.findAllByOrderId(id)
                .stream().map(this::convertToProductDTO).collect(Collectors.toList());
        orderDTO.setOrderProductsDTOList(orderProductDTOS);
        return orderDTO;
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id: " + id));
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO orderDTO, String username) {
        Person person = personService.getPersonByUsername(username);

        Order order = new Order();
        order.setOrderAddress(orderDTO.getOrderAddress());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setOrderTotal(orderDTO.getOrderTotal());
        order.setOrderType(orderDTO.getOrderType());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setPerson(person); // Установка пользователя
        Order savedOrder = orderRepository.save(order);
       orderDTO.getOrderProducts().stream()
                .map(createOrderProductDTO -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(productService.getProductById(createOrderProductDTO.getProduct())); // Получение продукта по ID
                    orderProduct.setProductQuantity(createOrderProductDTO.getProductQuantity());
                    orderProduct.setOrder(savedOrder); // Установка связи с заказом
                    orderProductRepository.save(orderProduct);
                    return orderProduct;
                })
                .collect(Collectors.toList());
        return getOrderDTOById(order.getId());
    }

    public OrderDTO updateOrder(Long orderId, CreateOrderDTO orderDTO) {
        Order existingOrder = getOrderById(orderId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(orderDTO, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    private OrderDTO convertToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
    private Order convertToEntity(Object orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
    private OrderProductDTO convertToProductDTO(OrderProduct order) {
        return modelMapper.map(order, OrderProductDTO.class);
    }
    private OrderProduct convertToProductEntity(Object orderDTO) {
        return modelMapper.map(orderDTO, OrderProduct.class);
    }

}