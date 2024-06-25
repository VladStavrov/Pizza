package modsen.interns.pizza_modsen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.REFRESH)
    private List<Order> orders;

    private double price;
    private String description;
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<AttributeValue> attributeValues= new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "product")
    private List<Cart> cartList= new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "product")
    private List<Order> orderList= new ArrayList<>();

}