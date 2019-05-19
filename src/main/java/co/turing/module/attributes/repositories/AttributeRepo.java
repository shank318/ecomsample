package co.turing.module.attributes.repositories;

import co.turing.module.attributes.domain.Attribute;
import co.turing.module.categories.domain.Category;
import co.turing.module.categories.domain.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute, Integer> {
    Attribute findByAttributeId(int id);

}
