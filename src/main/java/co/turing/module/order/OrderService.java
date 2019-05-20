package co.turing.module.order;

import co.turing.dto.request.CreateOrder;
import co.turing.dto.response.OrderInfo;
import co.turing.module.order.domain.Order;
import co.turing.module.order.domain.OrderShortDetail;

import java.util.List;

public interface OrderService {
    int createOrder(Order order);
    OrderInfo getOrder(int orderId);
    List<Order> getOrders(int customerId);
    OrderShortDetail getShortDetailOrder(int orderId);
}
