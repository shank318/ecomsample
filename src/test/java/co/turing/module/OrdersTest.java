package co.turing.module;

import co.turing.dto.response.CartItem;
import co.turing.dto.response.OrderInfo;
import co.turing.error.ApiException;
import co.turing.module.cart.CartService;
import co.turing.module.cart.CartServiceImpl;
import co.turing.module.order.OrderRepo;
import co.turing.module.order.OrderServiceImpl;
import co.turing.module.order.domain.Order;
import co.turing.module.payment.PaymentStatusStateMachine;
import co.turing.module.tax.TaxService;
import co.turing.module.tax.TaxServiceImpl;
import co.turing.module.tax.domain.Tax;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrdersTest {

    @Mock
    CartService cartService;

    @Mock
    TaxService taxService;

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepo orderRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    List<CartItem> cartItems = new ArrayList<>();

    @Test
    public void createOrder() {
        when(cartService.totalCartAmount(any())).thenReturn(10.0);
        Tax tax = new Tax();
        tax.setTaxId(1);
        tax.setTaxPercentage(10);
        when(taxService.getTax(1)).thenReturn(tax);
        Order order = new Order();
        order.setTaxId(1);
        when(orderRepo.save(any())).thenReturn(order);
        assertEquals(11.0 + "", orderService.createOrder(order).getTotalAmount() + "");
    }

    @Test
    public void getOrder() {
        addItems(new CartItemIMpl(1, "A", "attr", 1, 500));
        when(cartService.getCart(any())).thenReturn(cartItems);
        Order order = new Order();
        order.setOrderId(1);
        when(orderRepo.findByOrderId(1)).thenReturn(order);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder(order);
        orderInfo.setCartItems(cartItems);
        assertEquals(orderInfo, orderService.getOrder(1));
    }

    @Test(expected = ApiException.class)
    public void getOrderNotFounf() {
        addItems(new CartItemIMpl(1, "A", "attr", 1, 500));
        when(cartService.getCart(any())).thenReturn(cartItems);
        Order order = new Order();
        order.setOrderId(1);
        when(orderRepo.findByOrderId(1)).thenReturn(null);
        orderService.getOrder(1);
    }

    @Test
    public void updateOrderStatus() {
        Order order = new Order();
        order.setOrderId(1);
        order.setStatus(0);
        when(orderRepo.findByOrderId(1)).thenReturn(order);
        when(orderRepo.save(any())).thenReturn(order);
        final Order order1 = orderService.updateOrderStatus(1, PaymentStatusStateMachine.PAID);
        assertEquals(1, order1.getStatus());
    }

    @Test(expected = ApiException.class)
    public void updateOrderStatusRefundedToPaid() {
        Order order = new Order();
        order.setOrderId(1);
        order.setStatus(5);
        when(orderRepo.findByOrderId(1)).thenReturn(order);
        when(orderRepo.save(any())).thenReturn(order);
        final Order order1 = orderService.updateOrderStatus(1, PaymentStatusStateMachine.PAID);
        assertEquals(1, order1.getStatus());
    }

    @Test(expected = ApiException.class)
    public void updateOrderStatusPendingToRefunded() {
        Order order = new Order();
        order.setOrderId(1);
        order.setStatus(3);
        when(orderRepo.findByOrderId(1)).thenReturn(order);
        when(orderRepo.save(any())).thenReturn(order);
        final Order order1 = orderService.updateOrderStatus(1, PaymentStatusStateMachine.REFUNDED);
        assertEquals(1, order1.getStatus());
    }

    private void addItems(CartItem cartItem) {
        cartItems.add(cartItem);
    }
}
