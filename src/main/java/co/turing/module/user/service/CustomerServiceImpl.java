package co.turing.module.user.service;

import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.request.UpdateCustomer;
import co.turing.dto.response.CustomerAuthResponse;
import co.turing.dto.response.CustomerSchema;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.user.domain.Customer;
import co.turing.dto.request.UpdateAddress;
import co.turing.dto.request.UpdateCreditCard;
import co.turing.module.user.repository.CustomerRepository;
import co.turing.security.JwtTokenProvider;
import co.turing.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Create an user
     * @param user
     * @return
     */
    public CustomerAuthResponse signUp(Customer user) {
        if (!customerRepository.existsByEmail(user.getEmail())) {
            user.getEmail().toLowerCase();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final Customer customer = customerRepository.save(user);
            String token = jwtTokenProvider.createToken(customer.getCustomerId());
            CustomerAuthResponse response = new CustomerAuthResponse();
            CustomerSchema schema = new CustomerSchema();
            schema.setSchema(customer);
            response.setCustomer(schema);
            response.setAccessToken(token);
            return response;
        } else {
            throw new ApiException(TuringErrors.USER_ALREADY_EXISTS.getMessage(), TuringErrors.USER_ALREADY_EXISTS.getCode(), "email", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update address
     * @param updateAddress
     * @return
     */
    @Override
    public Customer updateAddress(UpdateAddress updateAddress) {
        Customer customer = customerRepository.findByCustomerId(updateAddress.getCustomerId());
        if (customer == null) {
            throw new ApiException(TuringErrors.EMAIL_NOTEXIST.getMessage(), TuringErrors.EMAIL_NOTEXIST.getCode(), "email");
        }
        customer.setAddress1(updateAddress.getAddress1());
        customer.setAddress2(updateAddress.getAddress2());
        customer.setCity(updateAddress.getCity());
        customer.setPostalCode(updateAddress.getPostalCode());
        customer.setRegion(updateAddress.getRegion());
        customer.setCountry(updateAddress.getCountry());
        customer.setShippingRegionId(updateAddress.getShippingRegionId());
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Login user
     * @param loginRequestDto
     * @return
     */
    @Override
    public CustomerAuthResponse login(CustomerLoginDto loginRequestDto) {
        try {
            loginRequestDto.getEmail().toLowerCase();
            final Customer customer = customerRepository.findByEmail(loginRequestDto.getEmail());
            if (customer == null) {
                throw new ApiException(TuringErrors.EMAIL_NOTEXIST.getMessage(), TuringErrors.EMAIL_NOTEXIST.getCode(), TuringErrors.EMAIL_NOTEXIST.getField());
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getCustomerId(), loginRequestDto.getPassword()));
            final String token = jwtTokenProvider.createToken(customer.getCustomerId());
            CustomerAuthResponse response = new CustomerAuthResponse();
            CustomerSchema schema = new CustomerSchema();
            schema.setSchema(customer);
            response.setCustomer(schema);
            response.setAccessToken(token);
            return response;
        } catch (AuthenticationException e) {
            throw new ApiException(TuringErrors.USER_PWD_INCORRECT.getMessage(), TuringErrors.USER_PWD_INCORRECT.getCode(), TuringErrors.USER_PWD_INCORRECT.getField());
        }
    }

    /**
     * Get a user by id
     * @param customerId
     * @return
     */
    @Override
    public Customer get(int customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    /**
     * Update a customer
     * @param updateCustomer
     * @return
     */
    @Override
    public Customer updateCustomer(UpdateCustomer updateCustomer) {
        Customer customer = customerRepository.findByCustomerId(updateCustomer.getCustomerId());
        if (customer == null) {
            throw new ApiException(TuringErrors.EMAIL_NOTEXIST.getMessage(), TuringErrors.EMAIL_NOTEXIST.getCode(), "email");
        }
        customer.setEmail(updateCustomer.getEmail());
        customer.setPassword(updateCustomer.getPassword());
        customer.setDayPhone(updateCustomer.getDayPhone());
        customer.setEvePhone(updateCustomer.getEvePhone());
        customer.setMobPhone(updateCustomer.getMobPhone());
        customer.setName(updateCustomer.getName());
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Update customer card
     * @param updateCreditCard
     * @return
     */
    @Override
    public Customer updateCreditCard(UpdateCreditCard updateCreditCard) {
        if (!Util.isValidCreditCard(updateCreditCard.getCreditCard())) {
            throw new ApiException(TuringErrors.INVALID_CARD.getMessage(), TuringErrors.INVALID_CARD.getCode(), TuringErrors.INVALID_CARD.getField());
        }
        Customer customer = customerRepository.findByCustomerId(updateCreditCard.getCustomerId());
        customer.setCreditCard(updateCreditCard.getCreditCard());
        customerRepository.save(customer);
        return customer;
    }
}
