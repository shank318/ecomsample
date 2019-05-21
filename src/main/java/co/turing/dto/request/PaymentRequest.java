package co.turing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentRequest {

    @ApiModelProperty(required = true)
    @NotBlank
    private String stripeToken;
    @ApiModelProperty(required = true)
    private int orderId;

    @ApiModelProperty(required = true)
    @NotBlank
    private String description;

    @ApiModelProperty(required = true)
    @NotBlank
    private int amount;

    @ApiModelProperty(required = true)
    @NotBlank
    private String currency;

    @JsonIgnore
    int customerId;
}
