package co.turing.dto.response;

import co.turing.module.user.domain.Customer;
import lombok.Data;

@Data
public class CustomerAuthResponse {
    String accessToken;
    String expires_in = "24h";
    CustomerSchema customer;
}


