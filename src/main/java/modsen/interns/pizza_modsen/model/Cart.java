package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import modsen.interns.pizza_modsen.cart.dto.CartProductDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Person user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts = new ArrayList<>();





}