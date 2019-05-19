package co.turing.module.user.controllers;

import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.request.CustomerSignUpDto;
import co.turing.error.ApiException;
import co.turing.module.user.domain.Customer;
import co.turing.module.user.domain.TokenResponse;
import co.turing.module.user.service.CustomerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Creates user account .", notes = "")
    public ResponseEntity registerUser(@Valid @RequestBody CustomerSignUpDto signUpRequestDto) throws ApiException {
        log.info("SignUpRequestDto request -->" + signUpRequestDto.toString());
        Customer customer = modelMapper.map(signUpRequestDto, Customer.class);
        return new ResponseEntity(customerService.signUp(customer), HttpStatus.OK);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Login user", notes = "")
    public ResponseEntity loginUser(@Valid @RequestBody CustomerLoginDto loginRequestDto) throws ApiException {
        log.info("LoginRequestDto request -->" + loginRequestDto.toString());
        return new ResponseEntity(customerService.login(loginRequestDto), HttpStatus.OK);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get Customer", notes = "")
    public ResponseEntity getCustomer() throws ApiException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return new ResponseEntity(customerService.get(Integer.valueOf(username)), HttpStatus.OK);

    }
}
