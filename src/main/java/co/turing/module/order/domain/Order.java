package co.turing.module.order.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "total_amount")
    private double totalAmount;
    private int status;
    private String comments;
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "auth_code")
    private String authCode;
    private String reference;
    @Column(name = "shipping_id")
    private int shippingId;
    @Column(name = "tax_id")
    private int taxId;
    @Column(name = "created_on")
    @CreationTimestamp
    LocalDateTime createdOn;
    @Column(name = "shipped_on")
    LocalDateTime shippedOn;
}
