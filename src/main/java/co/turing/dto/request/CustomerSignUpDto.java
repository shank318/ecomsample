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
    @NotBlank
    private String password;

    @ApiModelProperty(required = true)
    private String name;
}
