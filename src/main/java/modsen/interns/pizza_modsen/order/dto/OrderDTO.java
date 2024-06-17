package modsen.interns.pizza_modsen.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import modsen.interns.pizza_modsen.model.Order;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDTO {

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderAddress = order.getOrderAddress();
        this.orderStatus = order.getOrderStatus();
        this.orderTotal = order.getOrderTotal();
        this.orderType = order.getOrderType();
        this.paymentMethod = order.getPaymentMethod();
    }

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;
    private String orderAddress;
    private String orderStatus;
    private double orderTotal;
    private String orderType;
    private String paymentMethod;
}
