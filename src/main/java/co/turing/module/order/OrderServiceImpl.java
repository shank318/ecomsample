package co.turing.module.order;

import co.turing.dto.response.CartItem;
import co.turing.dto.response.OrderInfo;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.cart.CartService;
import co.turing.module.order.domain.Order;
import co.turing.module.order.domain.OrderShortDetail;
import co.turing.module.order.domain.OrderStatus;
import co.turing.module.shipping.ShippingService;
import co.turing.module.shipping.domain.Shipping;
import co.turing.module.tax.TaxService;
import co.turing.module.tax.domain.Tax;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    TaxService taxService;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    ShippingService shippingService;

    @Override
    public int createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED.getValue());
        Double amount = cartService.totalCartAmount(order.getReference());
        final Tax tax = taxService.getTax(order.getTaxId());
        amount = amount + (amount * tax.getTaxPercentage()) / 100;
        order.setTotalAmount(amount);
        Order created = orderRepo.save(order);
        return created.getOrderId();
    }

    @Override
    public OrderInfo getOrder(int orderId) {
        Order order = orderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new ApiException(TuringErrors.ORDER_NOT_FOUND.getMessage(), TuringErrors.ORDER_NOT_FOUND.getCode(), TuringErrors.ORDER_NOT_FOUND.getField());
        }
        final List<CartItem> cartItems = cartService.getCart(order.getReference());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder(order);
        orderInfo.setCartItems(cartItems);
        return orderInfo;
    }

    @Override
    public List<Order> getOrders(int customerId) {
        return orderRepo.findAllByCustomerId(customerId);
    }

    @Override
    public OrderShortDetail getShortDetailOrder(int orderId) {
        Order order = orderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new ApiException(TuringErrors.ORDER_NOT_FOUND.getMessage(), TuringErrors.ORDER_NOT_FOUND.getCode(), TuringErrors.ORDER_NOT_FOUND.getField());
        }
        OrderShortDetail orderShortDetail = modelMapper.map(order, OrderShortDetail.class);
        return orderShortDetail;
    }
}
