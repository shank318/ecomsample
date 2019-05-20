package co.turing.module.user.controllers;

import co.turing.dto.request.*;
import co.turing.error.ApiException;
import co.turing.module.user.domain.Customer;
import co.turing.module.user.service.CustomerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"User controller"})
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/customers", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Creates user account .", notes = "")
    public ResponseEntity registerUser(@Valid @RequestBody CustomerSignUpDto signUpRequestDto) throws ApiException, AuthenticationException {
        log.info("SignUpRequestDto request -->" + signUpRequestDto.toString());
        Customer customer = modelMapper.map(signUpRequestDto, Customer.class);
        return new ResponseEntity(customerService.signUp(customer), HttpStatus.OK);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Login user", notes = "")
    public ResponseEntity loginUser(@Valid @RequestBody CustomerLoginDto loginRequestDto) throws ApiException, AuthenticationException {
        log.info("LoginRequestDto request -->" + loginRequestDto.toString());
        return new ResponseEntity(customerService.login(loginRequestDto), HttpStatus.OK);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get Customer", notes = "")
    public ResponseEntity getCustomer(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) throws ApiException, AuthenticationException {
        return new ResponseEntity(customerService.get(Integer.parseInt(userDetails.getUsername())), HttpStatus.OK);

    }

    @RequestMapping(value = "/customers/address", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "Update address of a customer", notes = "")
    public ResponseEntity updateAddress(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateAddress updateAddress) throws ApiException, AuthenticationException {
        updateAddress.setCustomerId(Integer.parseInt(userDetails.getUsername()));
        return new ResponseEntity(customerService.updateAddress(updateAddress), HttpStatus.OK);

    }

    @RequestMapping(value = "/customers/creditCard", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "Update creditcard of a customer", notes = "")
    public ResponseEntity updateCreditCard(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateCreditCard updateCreditCard) throws ApiException, AuthenticationException {
        updateCreditCard.setCustomerId(Integer.parseInt(userDetails.getUsername()));
        return new ResponseEntity(customerService.updateCreditCard(updateCreditCard), HttpStatus.OK);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "Update Customer", notes = "")
    public ResponseEntity updateCustomer(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateCustomer updateCustomer) throws ApiException, AuthenticationException {
        updateCustomer.setCustomerId(Integer.parseInt(userDetails.getUsername()));
        return new ResponseEntity(customerService.updateCustomer(updateCustomer), HttpStatus.OK);

    }
}
