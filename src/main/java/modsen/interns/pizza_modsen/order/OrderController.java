package modsen.interns.pizza_modsen.order;


import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.order.dto.CreateOrderDTO;
import modsen.interns.pizza_modsen.order.dto.OrderDTO;
import modsen.interns.pizza_modsen.product.ProductController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/orders";
    public static final String ID_PATH = "/{id}";

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(ID_PATH)
    public Optional<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody CreateOrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping(ID_PATH)
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderDTO.setId(id);
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping(ID_PATH)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
