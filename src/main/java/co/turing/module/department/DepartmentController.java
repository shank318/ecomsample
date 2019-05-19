package co.turing.module.department;

import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.request.CustomerSignUpDto;
import co.turing.error.ApiException;
import co.turing.module.user.domain.Customer;
import co.turing.module.user.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Department controller"})
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/departments", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all departments .", notes = "")
    public ResponseEntity getAllDepartments() throws ApiException {
        return new ResponseEntity(departmentService.getAll(), HttpStatus.OK);

    }

    @RequestMapping(value = "/departments/{department_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Login user", notes = "")
    public ResponseEntity getDepartmentById(@ApiParam(value = "", required = true) @PathVariable(value = "department_id") int id) throws ApiException {
        return new ResponseEntity(departmentService.getDepartment(id), HttpStatus.OK);

    }
}
