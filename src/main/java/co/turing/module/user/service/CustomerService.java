package co.turing.module.user.service;


import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.request.UpdateCustomer;
import co.turing.dto.response.CustomerAuthResponse;
import co.turing.module.user.domain.Customer;
import co.turing.dto.request.UpdateAddress;
import co.turing.dto.request.UpdateCreditCard;

public interface CustomerService {


     CustomerAuthResponse signUp(Customer user);
     CustomerAuthResponse login(CustomerLoginDto loginRequestDto);
     Customer get(int customerId);
     Customer updateAddress(UpdateAddress updateAddress);
     Customer updateCreditCard(UpdateCreditCard updateAddress);
     Customer updateCustomer(UpdateCustomer updateCustomer);

}
