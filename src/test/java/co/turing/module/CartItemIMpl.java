package co.turing.module;

import co.turing.dto.response.CartItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemIMpl implements CartItem {

    private int itemId;
    private String name;
    private String attributes;
    private int product;
    private int price;

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAttributes() {
        return attributes;
    }

    @Override
    public int getProductId() {
        return product;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return 1;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public double getSubtotal() {
        return 0;
    }
}