package co.turing.module.order;

import co.turing.dto.response.CartItem;
import co.turing.dto.response.OrderInfo;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.cart.CartService;
import co.turing.module.order.domain.Order;
import co.turing.module.order.domain.OrderShortDetail;
import co.turing.module.payment.PaymentStatusStateMachine;
import co.turing.module.shipping.ShippingService;
import co.turing.module.tax.TaxService;
import co.turing.module.tax.domain.Tax;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
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
        order.setStatus(PaymentStatusStateMachine.INIT.getValue());
        Double amount = cartService.totalCartAmount(order.getReference());
        final Tax tax = taxService.getTax(order.getTaxId());
        amount = amount + (amount * tax.getTaxPercentage()) / 100;
        amount = Math.round(amount * 100.0) / 100.0;
        order.setTotalAmount(amount);
        order.setAuthCode("");
        order.setComments("");
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

    @Override
    public boolean updateOrderStatus(int orderId, PaymentStatusStateMachine paymentStatus) {
        Order order = orderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new ApiException(TuringErrors.ORDER_NOT_FOUND.getMessage(), TuringErrors.ORDER_NOT_FOUND.getCode(), TuringErrors.ORDER_NOT_FOUND.getField());
        }
        if (!PaymentStatusStateMachine.getEnumByString(order.getStatus()).nextState().contains(paymentStatus)) {
            throw new ApiException(TuringErrors.INVALID_ORDER_STATUS.getMessage(), TuringErrors.INVALID_ORDER_STATUS.getCode(), TuringErrors.INVALID_ORDER_STATUS.getField());
        }
        order.setStatus(paymentStatus.getValue());
        orderRepo.save(order);
        return true;
    }
}
