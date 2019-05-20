package co.turing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateCreditCard {
    @ApiModelProperty(required = true)
    @NotBlank
    String creditCard;

    @JsonIgnore
    int customerId;
}
