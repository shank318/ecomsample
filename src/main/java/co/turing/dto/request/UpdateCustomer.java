package co.turing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateCustomer {

    @JsonIgnore
    int customerId;

    @ApiModelProperty(required = true)
    @NotBlank(message = "email cannot be blank or null")
    @Email
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = "password cannot be blank or null")
    private String password;

    @ApiModelProperty(required = true)
    @NotBlank(message = "name cannot be blank or null")
    private String name;

    @ApiModelProperty(required = true)
    @NotBlank(message = "day_phone cannot be blank or null")
    private String dayPhone;
    @ApiModelProperty(required = true)
    @NotBlank(message = "eve_phone cannot be blank or null")
    private String evePhone;
    @ApiModelProperty(required = true)
    @NotBlank(message = "mob_phone cannot be blank or null")
    private String mobPhone;
}
