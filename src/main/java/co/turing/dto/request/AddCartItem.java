package co.turing.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCartItem {
    @NotBlank(message = "please enter a valid cart_id name")
    String cartId;
    int productId;
    @NotBlank(message = "please enter a valid attributes name")
    String attributes;
}
