package co.turing.module.attributes.service;

import co.turing.dto.response.ProductAttributes;
import co.turing.module.attributes.domain.Attribute;
import co.turing.module.attributes.domain.AttributeValue;

import java.util.List;

public interface AttributeService {
    List<Attribute> getAttributes();
    Attribute getAttribute(int id);
    List<AttributeValue> getAttributeValues(int attId);
    List<ProductAttributes> getProductAttributes(int productId);
}
