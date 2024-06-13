package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int productQuantity;
    private double totalCost;
}