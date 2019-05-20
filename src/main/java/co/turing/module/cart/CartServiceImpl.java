package co.turing.module.cart;

import co.turing.dto.request.AddCartItem;
import co.turing.dto.request.UpdateCart;
import co.turing.dto.response.CartItem;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.cart.domain.Cart;
import co.turing.module.user.domain.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String generateCartId() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    @Override
    public List<CartItem> addCartIten(AddCartItem addCartItem) {
        Cart cart = cartRepo.findByCartIdAndProductIdAndAttributes(addCartItem.getCartId(), addCartItem.getProductId(), addCartItem.getAttributes());
        if (cart == null) {
            cart = new Cart();
            cart.setAttributes(addCartItem.getAttributes());
            cart.setCartId(addCartItem.getCartId());
            cart.setProductId(addCartItem.getProductId());
            cart.setQuantity(1);
        } else {
            cart.setQuantity(cart.getQuantity() + 1);
        }
        cartRepo.save(cart);
        return cartRepo.getCartItems(addCartItem.getCartId());
    }

    @Override
    public List<CartItem> getCart(String cartId) {
        return cartRepo.getCartItems(cartId);
    }

    @Override
    public List<CartItem> updateCart(UpdateCart updateCart) {
        Cart cart = cartRepo.findByItemId(updateCart.getItemId());
        if (cart == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
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
    public Double totalCartAmount(String cartId) {
        final Double cartValue = cartRepo.getCartValue(cartId);
        if (cartValue == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
        return cartValue;
    }

    @Override
    public List<CartItem> removeCartItem(int itemId) {
        Cart cart = cartRepo.findByItemId(itemId);
        if (cart == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
        cartRepo.delete(cart);
        return cartRepo.getCartItems(cart.getCartId());
    }
}
