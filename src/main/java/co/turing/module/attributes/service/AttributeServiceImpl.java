package co.turing.module.attributes.service;

import co.turing.dto.response.ProductAttributes;
import co.turing.error.ApiException;
import co.turing.module.attributes.domain.Attribute;
import co.turing.module.attributes.domain.AttributeValue;
import co.turing.module.attributes.repositories.AttributeRepo;
import co.turing.module.attributes.repositories.AttributeValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames={"attributes"})
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    AttributeRepo attributeRepo;

    @Autowired
    AttributeValueRepo attributeValueRepo;

    @Override
    @Cacheable
    public List<Attribute> getAttributes() {
        return attributeRepo.findAll();
    }

    @Override
    @Cacheable
    public Attribute getAttribute(int id) {
        final Attribute one = attributeRepo.findByAttributeId(id);
        return one;
    }

    @Override
    @Cacheable
    public List<AttributeValue> getAttributeValues(int attId) {
        return attributeValueRepo.findAllByAttributeId(attId);
    }

    @Override
    @Cacheable
    public List<ProductAttributes> getProductAttributes(int productId) {
        return attributeValueRepo.findAllByProductId(productId);
    }
}
