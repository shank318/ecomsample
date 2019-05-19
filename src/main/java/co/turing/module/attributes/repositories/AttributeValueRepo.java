package co.turing.module.attributes.repositories;

import co.turing.dto.response.ProductAttributes;
import co.turing.dto.response.ProductCategoriesInfo;
import co.turing.module.attributes.domain.Attribute;
import co.turing.module.attributes.domain.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepo extends JpaRepository<AttributeValue, Integer> {
    List<AttributeValue> findAllByAttributeId(int attributeId);

    @Query(value = "SELECT a.name AS attributeName,\n" +
            "             av.attribute_value_id AS attributeValueId, av.value AS attributeValue\n" +
            "  FROM       attribute_value av\n" +
            "  INNER JOIN attribute a\n" +
            "               ON av.attribute_id = a.attribute_id\n" +
            "  WHERE      av.attribute_value_id IN\n" +
            "               (SELECT attribute_value_id\n" +
            "                FROM   product_attribute\n" +
            "                WHERE  product_id = :product_id)\n" +
            "  ORDER BY   a.name", nativeQuery = true)
    List<ProductAttributes> findAllByProductId(@Param("product_id") int productId);


}
