package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Entity
@Data
public class OrderProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "order_id")
    private Order order;

    @Min(1)
    @Column(nullable = false)
    private int productQuantity;
}