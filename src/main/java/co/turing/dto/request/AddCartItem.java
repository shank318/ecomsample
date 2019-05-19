package co.turing.dto.request;

import lombok.Data;

@Data
public class AddCartItem {
    String cartId;
    int productId;
    String attributes;
}
