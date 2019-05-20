package co.turing.dto.response;

import co.turing.module.order.domain.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderInfo {
    Order order;
    List<CartItem> cartItems;
}
