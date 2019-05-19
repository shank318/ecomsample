package co.turing.module.cart;

import co.turing.dto.request.AddCartItem;
import co.turing.dto.request.UpdateCart;
import co.turing.dto.response.CartItem;
import co.turing.module.cart.domain.Cart;
import co.turing.module.user.domain.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String generateCartId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public List<CartItem> addCartIten(AddCartItem addCartItem) {
        cartRepo.addCartItem(addCartItem.getCartId(), addCartItem.getProductId(), addCartItem.getAttributes());
        return cartRepo.getCartItems(addCartItem.getCartId());
    }

    @Override
    public List<CartItem> getCart(String cartId) {
        return cartRepo.getCartItems(cartId);
    }

    @Override
    public List<CartItem> updateCart(UpdateCart updateCart) {
        Cart cart = cartRepo.findByItemId(updateCart.getItemId());
        if (updateCart.getQuantity() == 0) {
            cartRepo.deleteItemFromCart(updateCart.getItemId());
        } else {
            cartRepo.addQuantityOfCartItem(updateCart.getItemId(), updateCart.getQuantity());
        }
        return cartRepo.getCartItems(cart.getCartId());
    }

    @Override
    public List<CartItem> deleteCart(String cartId) {
        cartRepo.deleteCart(cartId);
        return cartRepo.getCartItems(cartId);
    }

    @Override
    public int totalCartAmount(String cartId) {
        return cartRepo.getCartValue(cartId);
    }

    @Override
    public List<CartItem> removeCartItem(int itemId) {
        Cart cart = cartRepo.findByItemId(itemId);
        cartRepo.delete(cart);
        return cartRepo.getCartItems(cart.getCartId());
    }
}
