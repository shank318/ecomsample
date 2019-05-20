package co.turing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateAddress {

    @NotBlank
    @ApiModelProperty(required = true)
    private String address1;
    private String address2;
    @NotBlank(message = "please enter a valid city name")
    @ApiModelProperty(required = true)
    private String city;
    @NotBlank(message = "please enter a valid region name")
    @ApiModelProperty(required = true)
    private String region;
    @NotBlank(message = "please enter a valid postal_code name")
    @ApiModelProperty(required = true)
    private String postalCode;
    @NotBlank(message = "please enter a valid country name")
    @ApiModelProperty(required = true)
    private String country;


    @ApiModelProperty(required = true)
    private int shippingRegionId;

    @JsonIgnore
    private int customerId;
}
