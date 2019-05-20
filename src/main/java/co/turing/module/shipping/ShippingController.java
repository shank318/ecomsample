package co.turing.module.shipping;


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
@RequestMapping("/shipping")
@Slf4j
@Api(tags = {"Shipping region controller"})
public class ShippingController {

    @Autowired
    ShippingService shippingService;

    @RequestMapping(value = "/regions", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all shipping regions .", notes = "")
    public ResponseEntity getAll() throws ApiException {
        return new ResponseEntity(shippingService.getAllShippingDetails(), HttpStatus.OK);

    }

    @RequestMapping(value = "/regions/{shipping_region_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get Shipping region By Id", notes = "")
    public ResponseEntity getDepartmentById(@ApiParam(value = "", required = true) @PathVariable(value = "shipping_region_id") int id) throws ApiException {
        return new ResponseEntity(shippingService.getShippingRegion(id), HttpStatus.OK);

    }
}
