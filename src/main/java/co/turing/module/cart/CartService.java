package co.turing.module.cart;

import co.turing.dto.request.AddCartItem;
import co.turing.dto.request.UpdateCart;
import co.turing.dto.response.CartItem;

import java.util.List;

public interface CartService {

    String generateCartId();

    List<CartItem> addCartIten(AddCartItem addCartItem);

    List<CartItem> getCart(String cartId);

    List<CartItem> updateCart(UpdateCart updateCart);

    List<CartItem> deleteCart(String cartId);

    int totalCartAmount(String cartId);

    List<CartItem> removeCartItem(int itemId);
}

