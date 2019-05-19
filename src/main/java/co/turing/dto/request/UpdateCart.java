package co.turing.dto.request;

import lombok.Data;

@Data
public class UpdateCart {
    private int itemId;
    int quantity;
}