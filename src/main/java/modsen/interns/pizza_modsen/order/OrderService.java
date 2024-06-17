package modsen.interns.pizza_modsen.order;

import modsen.interns.pizza_modsen.model.Order;
import modsen.interns.pizza_modsen.order.dto.CreateOrderDTO;
import modsen.interns.pizza_modsen.order.dto.OrderDTO;
import modsen.interns.pizza_modsen.person.dto.PersonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    OrderRepository orderRepository;
    ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDTO::new);
    }

    public OrderDTO createOrder(CreateOrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderAddress(orderDTO.getOrderAddress());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setOrderTotal(orderDTO.getOrderTotal());
        order.setOrderType(orderDTO.getOrderType());
        order.setPaymentMethod(orderDTO.getPaymentMethod());

        Order savedOrder = orderRepository.save(order);
        return new OrderDTO(savedOrder);
    }

    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id: " + orderId));

        if (orderDTO.getOrderDate() != null) {
            existingOrder.setOrderDate(orderDTO.getOrderDate());
        }
        if (orderDTO.getOrderAddress() != null) {
            existingOrder.setOrderAddress(orderDTO.getOrderAddress());
        }
        if (orderDTO.getOrderStatus() != null) {
            existingOrder.setOrderStatus(orderDTO.getOrderStatus());
        }
        if (orderDTO.getOrderTotal() != 0) {
            existingOrder.setOrderTotal(orderDTO.getOrderTotal());
        }
        if (orderDTO.getOrderType() != null) {
            existingOrder.setOrderType(orderDTO.getOrderType());
        }
        if (orderDTO.getPaymentMethod() != null) {
            existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return new OrderDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


}
