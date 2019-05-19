package co.turing.module.products.domain;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductDetail {
    private int productId;
    private String name;
    private String description;
    private double price;
    private String thumbnail;
    private double discountedPrice;
}
