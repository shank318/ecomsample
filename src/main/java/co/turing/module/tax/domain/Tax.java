package co.turing.module.tax.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tax")
public class Tax {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private int taxId;

    @Column(name = "tax_type")
    String taxType;

    @Column(name = "tax_percentage")
    double taxPercentage;
}
