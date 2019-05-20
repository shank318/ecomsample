package co.turing.module.tax;


import co.turing.error.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Tax controller"})
public class TaxController {

    @Autowired
    TaxService taxService;

    @RequestMapping(value = "/tax", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all taxes .", notes = "")
    public ResponseEntity getAll() throws ApiException {
        return new ResponseEntity(taxService.getTaxes(), HttpStatus.OK);

    }

    @RequestMapping(value = "/tax/{tax_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get Tax By Id", notes = "")
    public ResponseEntity getDepartmentById(@ApiParam(value = "", required = true) @PathVariable(value = "tax_id") int id) throws ApiException {
        return new ResponseEntity(taxService.getTax(id), HttpStatus.OK);

    }
}
