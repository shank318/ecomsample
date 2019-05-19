package co.turing.module.user.service;


import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.response.CustomerAuthResponse;
import co.turing.module.user.domain.Customer;

public interface CustomerService {


     CustomerAuthResponse signUp(Customer user);
     CustomerAuthResponse login(CustomerLoginDto loginRequestDto);
     Customer get(Integer customerId);

}
