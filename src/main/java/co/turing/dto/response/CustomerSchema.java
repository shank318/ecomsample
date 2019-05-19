package co.turing.dto.response;

import co.turing.module.user.domain.Customer;
import lombok.Data;

@Data
public class CustomerSchema {
    public Customer schema;
}
