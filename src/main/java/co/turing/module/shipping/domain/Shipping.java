package co.turing.module.shipping.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shipping_region")
public class Shipping {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_region_id")
    private int shippingRegionId;

    @Column(name = "shipping_region")
    String shippingRegion;

}
