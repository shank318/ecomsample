package co.turing.module.categories.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategories  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private String productId;
    @Column(name = "category_id")
    private String categoryId;
}
