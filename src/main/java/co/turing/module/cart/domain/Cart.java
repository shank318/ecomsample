package co.turing.module.cart.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shopping_cart")
public class Cart {


    @Column(name = "cart_id")
    private String cartId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "product_id")
    private int productId;
    private String attributes;
    private int quantity;
    @Column(name = "buy_now")
    private boolean buyNow = true;

    @Column(name = "added_on")
    @CreationTimestamp
    LocalDateTime addedOn;
}
