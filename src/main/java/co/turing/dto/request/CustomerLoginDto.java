package co.turing.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CustomerLoginDto {

    @ApiModelProperty(required = true)
    @NotBlank(message = "email cannot be blank or null")
    @Email
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = "password cannot be blank or null")
    private String password;
}
