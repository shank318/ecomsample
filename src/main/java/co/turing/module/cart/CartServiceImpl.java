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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheNames={"cart"})
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;



    @Override
    public String generateCartId() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    /**
     *
     * @param addCartItem
     * @return
     */
    @Override
    @CacheEvict(key = "#addCartItem.cartId")
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
        return getCart(addCartItem.getCartId());
    }

    /**
     *
     * @param cartId
     * @return
     */
    @Override
    @Cacheable
    public List<CartItem> getCart(String cartId) {
        return cartRepo.getCartItems(cartId);
    }

    /**
     *
     * @param updateCart
     * @return
     */
    @Override
    public List<CartItem> updateCart(UpdateCart updateCart) {
        Cart cart = cartRepo.findByItemId(updateCart.getItemId());
        if (cart == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
        if (updateCart.getQuantity() == 0) {
            cartRepo.deleteItemFromCart(updateCart.getItemId());
        } else {
            cartRepo.addQuantityOfCartItem(updateCart.getItemId(), updateCart.getQuantity());
        }
        return getCart(cart.getCartId());
    }

    /**
     *
     * @param cartId
     * @return
     */
    @Override
    @CacheEvict(key = "#cartId")
    public List<CartItem> deleteCart(String cartId) {
        cartRepo.deleteCart(cartId);
        return getCart(cartId);
    }

    /**
     *
     * @param cartId
     * @return
     */
    @Override
    public Double totalCartAmount(String cartId) {
        final Double cartValue = cartRepo.getCartValue(cartId);
        if (cartValue == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
        return cartValue;
    }

    /**
     *
     * @param itemId
     * @return
     */
    @Override
    public List<CartItem> removeCartItem(int itemId) {
        Cart cart = cartRepo.findByItemId(itemId);
        if (cart == null) throw new ApiException(TuringErrors.CART_ITEM_NOT_FOUND.getMessage(),TuringErrors.CART_ITEM_NOT_FOUND.getCode(),TuringErrors.CART_ITEM_NOT_FOUND.getField());
        cartRepo.delete(cart);
        return getCart(cart.getCartId());
    }
}
