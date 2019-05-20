package co.turing.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrder {
    @ApiModelProperty(required = true)
    @NotBlank
    private String cartId;
    @ApiModelProperty(required = true)
    private int shippingId;
    @ApiModelProperty(required = true)
    private int taxId;


}
