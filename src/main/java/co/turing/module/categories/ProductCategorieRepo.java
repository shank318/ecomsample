package co.turing.module.categories;

import co.turing.dto.response.ProductCategoriesInfo;
import co.turing.module.categories.domain.Category;
import co.turing.module.categories.domain.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface ProductCategorieRepo extends JpaRepository<ProductCategories, Integer> {

    @Query(value = "SELECT c.category_id AS category_id, c.name AS name,  c.department_id AS department_id FROM category c JOIN product_category pc ON c.category_id = pc.category_id WHERE pc.product_id =:product_id", nativeQuery = true)
    List<ProductCategoriesInfo> findAllByProductId(@Param("product_id") int productId);
}
