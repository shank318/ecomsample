package co.turing.module.attributes;

import co.turing.dto.response.ProductAttributes;
import co.turing.error.ApiException;
import co.turing.module.attributes.domain.Attribute;
import co.turing.module.attributes.domain.AttributeValue;
import co.turing.module.attributes.service.AttributeService;
import co.turing.module.categories.CategoriesService;
import co.turing.module.categories.ProductCategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Attributes controller"})
public class AttributesController {

    @Autowired
    AttributeService attributeService;


    @RequestMapping(value = "/attributes", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all attributes .", notes = "", response = Attribute.class,
            responseContainer = "List")
    public ResponseEntity getAllAttributes() throws ApiException {
        return new ResponseEntity(attributeService.getAttributes(), HttpStatus.OK);

    }

    @RequestMapping(value = "/attributes/{attribute_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get a attribute", notes = "", response = Attribute.class)
    public ResponseEntity getAttributesById(@ApiParam(value = "", required = true) @PathVariable(value = "attribute_id") int id) throws ApiException {
        return new ResponseEntity(attributeService.getAttribute(id), HttpStatus.OK);

    }

    @RequestMapping(value = "/attributes/values/{attribute_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values", notes = "", response = AttributeValue.class,
            responseContainer = "List")
    public ResponseEntity getValuesByAttributeId(@ApiParam(value = "", required = true) @PathVariable(value = "attribute_id") int id) throws ApiException {
        return new ResponseEntity(attributeService.getAttributeValues(id), HttpStatus.OK);

    }

    @RequestMapping(value = "/attributes/inProduct/{product_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "", response = ProductAttributes.class,
            responseContainer = "List")
    public ResponseEntity getAttributesByProductId(@ApiParam(value = "", required = true) @PathVariable(value = "product_id") int id) throws ApiException {
        return new ResponseEntity(attributeService.getProductAttributes(id), HttpStatus.OK);

    }
}
