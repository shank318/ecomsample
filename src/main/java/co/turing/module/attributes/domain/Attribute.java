package co.turing.module.attributes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private int attributeId;
    private String name;
}
