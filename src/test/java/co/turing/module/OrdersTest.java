package co.turing.module;

import co.turing.dto.response.CartItem;
import co.turing.dto.response.OrderInfo;
import co.turing.module.cart.CartService;
import co.turing.module.cart.CartServiceImpl;
import co.turing.module.order.OrderRepo;
import co.turing.module.order.OrderServiceImpl;
import co.turing.module.order.domain.Order;
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
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    List<CartItem> cartItems = new ArrayList<>();
    @Test
    public void createOrder(){
        when(cartService.totalCartAmount(any())).thenReturn(10.0);
        Tax tax = new Tax();
        tax.setTaxId(1);
        tax.setTaxPercentage(10);
        when(taxService.getTax(any())).thenReturn(tax);
        Order order = new Order();
        when(orderRepo.save(any())).thenReturn(order);
        assertEquals(11, orderService.createOrder(order).getTotalAmount());
    }

    @Test
    public void getOrder(){
        addItems(new CartItemIMpl(1,"A","attr",1,500));
        when(cartService.getCart(any())).thenReturn(cartItems);
        Order order = new Order();
        order.setOrderId(1);
        when(orderRepo.findByOrderId(any())).thenReturn(order);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder(order);
        orderInfo.setCartItems(cartItems);
        assertEquals(orderInfo, orderService.getOrder(1));
    }

    private void addItems(CartItem cartItem){
        cartItems.add(cartItem);
    }
}
