package co.turing.module.products.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "name")
    private String name;
    private String description;
    private double price;
    @Column(name = "discounted_price")
    private double discountedPrice;
    private String image;
    @Column(name = "image_2")
    private String image2;
    private String thumbnail;
    private int display;
}
