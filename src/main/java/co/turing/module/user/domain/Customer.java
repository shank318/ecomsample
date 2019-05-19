package co.turing.module.user.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "customer")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
    @Column(name = "address_1")
    private String address1;
    @Column(name = "address_2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "credit_card")
    private String creditCard;
    @Column(name = "region")
    private String region;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "country")
    private String country;
    @Column(name = "shipping_region_id")
    private int shippingRegionId;
    @Column(name = "day_phone")
    private String dayPhone;
    @Column(name = "eve_phone")
    private String evePhone;
    @Column(name = "mob_phone")
    private String mobPhone;

}
