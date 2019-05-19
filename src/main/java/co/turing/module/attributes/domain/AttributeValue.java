package co.turing.module.attributes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attribute_value")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_value_id")
    private int attributeValueId;
    private String value;
    @Column(name = "attribute_id")
    private int attributeId;
}
