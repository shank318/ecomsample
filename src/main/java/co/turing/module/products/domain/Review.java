package co.turing.module.products.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    @JsonIgnore
    int reviewId;

    @Column(name = "customer_id")
    @JsonIgnore
    int customerId;

    @Column(name = "product_id")
    @JsonIgnore
    int productId;

    @NotBlank
    String review;

    int rating;

    @Column(name = "created_on")
    @CreationTimestamp
    LocalDateTime createdOn;

}
