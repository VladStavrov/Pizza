package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class CartProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @Min(1)
    @Column(nullable = false)
    private int productQuantity;




}
