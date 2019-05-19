package co.turing.module.user.service;

import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.response.CustomerAuthResponse;
import co.turing.dto.response.CustomerSchema;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.user.domain.Customer;
import co.turing.module.user.repository.CustomerRepository;
import co.turing.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public CustomerAuthResponse signUp(Customer user) {
        if (!customerRepository.existsByEmail(user.getEmail())) {
            user.getEmail().toLowerCase();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final Customer customer = customerRepository.save(user);
            String token = jwtTokenProvider.createToken(user.getEmail());
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

    @Override
    public CustomerAuthResponse login(CustomerLoginDto loginRequestDto) {
        try {
            loginRequestDto.getEmail().toLowerCase();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
            final String token = jwtTokenProvider.createToken(loginRequestDto.getEmail());
            final Customer user = customerRepository.findByEmail(loginRequestDto.getEmail());
            CustomerAuthResponse response = new CustomerAuthResponse();
            CustomerSchema schema = new CustomerSchema();
            schema.setSchema(user);
            response.setCustomer(schema);
            response.setAccessToken(token);
            return response;
        } catch (AuthenticationException e) {
            if (e instanceof UsernameNotFoundException) {
                throw new ApiException(TuringErrors.EMAIL_NOTEXIST.getMessage(), TuringErrors.EMAIL_NOTEXIST.getCode(), "email");
            }
            throw new ApiException(TuringErrors.USER_PWD_INCORRECT.getMessage(), TuringErrors.USER_PWD_INCORRECT.getCode(), "password");
        }
    }

    @Override
    public Customer get(Integer customerId) {
        final Customer one = customerRepository.getOne(customerId);
        return customerRepository.getOne(customerId);
    }
}
