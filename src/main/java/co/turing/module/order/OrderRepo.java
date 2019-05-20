package co.turing.module.order;

import co.turing.module.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Integer> {
    List<Order> findAllByCustomerId(int customerId);
    Order findByOrderId(int orderId);


}
