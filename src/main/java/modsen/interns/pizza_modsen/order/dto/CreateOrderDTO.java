package modsen.interns.pizza_modsen.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateOrderDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;
    private String orderAddress;
    private String orderStatus;
    private double orderTotal;
    private String orderType;
    private String paymentMethod;
}
