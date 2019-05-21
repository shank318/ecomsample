package co.turing.module;

import co.turing.dto.response.CartItem;
import co.turing.module.cart.CartRepo;
import co.turing.module.cart.CartService;
import co.turing.module.cart.CartServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.Before;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartTest {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    CartRepo cartRepo;

    @InjectMocks
    CartServiceImpl cartService;



    List<CartItem> cartItems = new ArrayList<>();
    @Test
    public void testGetCart(){
        addItems(new CartItemIMpl(1,"A","attr",1,500));
        when(cartRepo.getCartItems(any())).thenReturn(cartItems);
        assertEquals(1, cartService.getCart(any()).size());
    }

    @Test
    public void testCartAmount(){
        when(cartRepo.getCartValue(any())).thenReturn(5.0);
        assertEquals(5.0+"", cartService.totalCartAmount(any())+"");
    }

    private void addItems(CartItem cartItem){
        cartItems.add(cartItem);
    }



}
