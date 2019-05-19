package co.turing.module.products;

import co.turing.dto.response.ProductSearchQueryResponse;
import co.turing.module.attributes.domain.Attribute;
import co.turing.module.products.domain.Product;
import co.turing.module.products.domain.ProductDepartment;
import co.turing.module.products.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Integer> {

    Product findByProductId(int productId);

    @Query(value = "SELECT p.product_id AS productId, p.name,\n" +
            "                     IF(LENGTH(p.description) <= :length,\n" +
            "                        p.description,\n" +
            "                        CONCAT(LEFT(p.description, :length),\n" +
            "                               '...')) AS description,\n" +
            "                     p.price, p.discounted_price AS discountedPrice, p.thumbnail\n" +
            "       FROM            product p\n" +
            "       WHERE    MATCH (name, description)\n" +
            "                AGAINST (:query IN BOOLEAN MODE)\n" +
            "       ORDER BY MATCH (name, description)\n" +
            "                AGAINST (:query IN BOOLEAN MODE) DESC\n" +
            "       LIMIT    :page, :limit", nativeQuery = true)
    List<ProductSearchQueryResponse> searchProductsAllWordOn(@Param("query") String query, @Param("length") int inShortProductDescriptionLength, @Param("page") int page, @Param("limit") int limit);


    @Query(value = "SELECT  p.product_id AS productId, p.name,\n" +
            "                     IF(LENGTH(p.description) <= :length,\n" +
            "                        p.description,\n" +
            "                        CONCAT(LEFT(p.description, :length),\n" +
            "                               '...')) AS description,\n" +
            "                     p.price, p.discounted_price AS discountedPrice, p.thumbnail\n" +
            "       FROM            product p\n" +
            "       WHERE    MATCH (name, description)\n" +
            "                AGAINST (:query)\n" +
            "       ORDER BY MATCH (name, description)\n" +
            "                AGAINST (:query) DESC\n" +
            "       LIMIT    :page, :limit", nativeQuery = true)
    List<ProductSearchQueryResponse> searchProductsAllWordOff(@Param("query") String query, @Param("length") int inShortProductDescriptionLength, @Param("page") int page, @Param("limit") int limit);


    @Query(value = "SELECT   count(*)\n" +
            "       FROM     product\n" +
            "       WHERE    MATCH (name, description) AGAINST (:query IN BOOLEAN MODE)", nativeQuery = true)
    int countSearchResultsWordOn(@Param("query") String query);

    @Query(value = "SELECT   count(*)\n" +
            "       FROM     product\n" +
            "       WHERE    MATCH (name, description) AGAINST (:query)", nativeQuery = true)
    int countSearchResultsWordOff(@Param("query") String query);


    @Query(value = "SELECT DISTINCT p.product_id AS productId, p.name,\n" +
            "                     IF(LENGTH(p.description) <= :length,\n" +
            "                        p.description,\n" +
            "                        CONCAT(LEFT(p.description, :length),\n" +
            "                               '...')) AS description,\n" +
            "                     p.price, p.discounted_price AS discountedPrice, p.thumbnail\n" +
            "     FROM            product p\n" +
            "     INNER JOIN      product_category pc\n" +
            "                       ON p.product_id = pc.product_id\n" +
            "     INNER JOIN      category c\n" +
            "                       ON pc.category_id = c.category_id\n" +
            "     WHERE           (p.display = 2 OR p.display = 3)\n" +
            "                     AND c.department_id = :did\n" +
            "     LIMIT           :page, :limit", nativeQuery = true)
    List<ProductDepartment> getProductsFromDepartment(@Param("did") int did, @Param("length") int inShortProductDescriptionLength, @Param("page") int page, @Param("limit") int limit);


    @Query(value = "SELECT DISTINCT COUNT(*) AS products_on_department_count\n" +
            "  FROM            product p\n" +
            "  INNER JOIN      product_category pc\n" +
            "                    ON p.product_id = pc.product_id\n" +
            "  INNER JOIN      category c\n" +
            "                    ON pc.category_id = c.category_id\n" +
            "  WHERE           (p.display = 2 OR p.display = 3)\n" +
            "                  AND c.department_id = :did", nativeQuery = true)
    int countProductsFromDepartment(@Param("did") int did);


    @Query(value = "SELECT   p.product_id AS productId, p.name,\n" +
            "               IF(LENGTH(p.description) <= :length,\n" +
            "                  p.description,\n" +
            "                  CONCAT(LEFT(p.description, :length),\n" +
            "                         '...')) AS description,\n" +
            "               p.price, p.discounted_price AS discountedPrice, p.thumbnail\n" +
            "    FROM       product p\n" +
            "    INNER JOIN product_category pc\n" +
            "                 ON p.product_id = pc.product_id\n" +
            "    WHERE      pc.category_id = :cid\n" +
            "    LIMIT      :page, :limit", nativeQuery = true)
    List<ProductDepartment> getProductsFromCategories(@Param("cid") int catId, @Param("length") int inShortProductDescriptionLength, @Param("page") int page, @Param("limit") int limit);

    @Query(value = "SELECT     COUNT(*) AS categories_count\n" +
            "  FROM       product p\n" +
            "  INNER JOIN product_category pc\n" +
            "               ON p.product_id = pc.product_id\n" +
            "  WHERE      pc.category_id = :cid", nativeQuery = true)
    int countProductsFromCategory(@Param("cid") int catId);


}
