package co.turing.module.cart;

import co.turing.dto.response.CartItem;
import co.turing.module.cart.domain.Cart;
import co.turing.module.categories.domain.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT     sc.item_id AS itemId, p.name,p.image, sc.attributes,\n" +
            "             COALESCE(NULLIF(p.discounted_price, 0), p.price) AS price,\n" +
            "             sc.quantity,\n" +
            "             COALESCE(NULLIF(p.discounted_price, 0),\n" +
            "                      p.price) * sc.quantity AS subtotal\n" +
            "  FROM       shopping_cart sc\n" +
            "  INNER JOIN product p\n" +
            "               ON sc.product_id = p.product_id\n" +
            "  WHERE      sc.cart_id = :cart_id AND sc.buy_now", nativeQuery = true)
    List<CartItem> getCartItems(@Param("cart_id") String cartId);

    @Query(value = "SELECT quantity\n" +
            "  FROM   shopping_cart\n" +
            "  WHERE  cart_id = :cart_id\n" +
            "         AND product_id = :pid\n" +
            "         AND attributes = :att\n" +
            "  INTO   productQuantity;\n" +
            "  IF productQuantity IS NULL THEN\n" +
            "    INSERT INTO shopping_cart(item_id, cart_id, product_id, attributes,\n" +
            "                              quantity, added_on)\n" +
            "           VALUES (UUID(), :cart_id, :pid, :att, 1, NOW());\n" +
            "  ELSE\n" +
            "    UPDATE shopping_cart\n" +
            "    SET    quantity = quantity + 1, buy_now = true\n" +
            "    WHERE  cart_id = :cart_id\n" +
            "           AND product_id = :pid\n" +
            "           AND attributes = :att", nativeQuery = true)
    void addCartItem(@Param("cart_id") String cartId, @Param("pid") int productId, @Param("att") String attributes);


    @Modifying
    @Transactional
    @Query(value = "update shopping_cart set qty=:quantity  where item_id = :cart_item_id", nativeQuery = true)
    int addQuantityOfCartItem(@Param("cart_item_id") int cartItemId, @Param("quantity") int quantity);

    @Query(value = "DELETE FROM shopping_cart WHERE item_id = :cart_item_id",nativeQuery = true)
    void deleteItemFromCart(@Param("cart_item_id") int cartItemId);

    @Query(value = "DELETE FROM shopping_cart WHERE cart_id = :cart_id",nativeQuery = true)
    void deleteCart(@Param("cart_id") String cartId);

    Cart findByItemId(int itemId);

    @Query(value = "SELECT     SUM(COALESCE(NULLIF(p.discounted_price, 0), p.price)\n" +
            "                 * sc.quantity) AS total_amount\n" +
            "  FROM       shopping_cart sc\n" +
            "  INNER JOIN product p\n" +
            "               ON sc.product_id = p.product_id\n" +
            "  WHERE      sc.cart_id = :cart_id AND sc.buy_now",nativeQuery = true)
    int getCartValue(@Param("cart_id") String cartId);

}
