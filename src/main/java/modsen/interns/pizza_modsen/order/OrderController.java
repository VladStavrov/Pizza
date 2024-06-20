package modsen.interns.pizza_modsen.order;


import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.order.dto.CreateOrderDTO;
import modsen.interns.pizza_modsen.order.dto.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(OrderController.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/orders";
    public static final String ID_PATH = "/{id}";

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<OrderDTO> orderDTO = orderService.getOrderDTOById(id);
        return orderDTO.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderDTO.setId(id);
        OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
