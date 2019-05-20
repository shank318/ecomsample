package co.turing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateCart {
    @JsonIgnore
    private int itemId;
    int quantity;
}