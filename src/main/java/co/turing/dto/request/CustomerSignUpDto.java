package co.turing.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CustomerSignUpDto {
    @ApiModelProperty(required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = "password cannot be blank or null")
    private String password;

    @ApiModelProperty(required = true)
    @NotBlank(message = "name cannot be blank or null")
    private String name;
}
