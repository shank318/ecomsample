package co.turing.module.products;


import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.attributes.service.AttributeService;
import co.turing.module.products.domain.Review;
import co.turing.module.products.service.ProductService;
import co.turing.module.products.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Product controller"})
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ReviewService reviewService;


    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all products .", notes = "")
    public ResponseEntity getAllProducts(@RequestParam(required = false, value = "description_length") Integer descriptionLength, @RequestParam(required = false, value = "page") Integer page, @RequestParam(required = false, value = "limit") Integer limit) throws ApiException {
        if (descriptionLength == null) descriptionLength = 200;
        return new ResponseEntity(productService.getProducts(getPageable(page, limit), descriptionLength), HttpStatus.OK);

    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get a attribute", notes = "")
    public ResponseEntity search(@RequestParam(required = false, value = "all_words") String allWords, @RequestParam(required = true, value = "query_string") String query, @RequestParam(required = false, value = "description_length") Integer descriptionLength, @RequestParam(required = false, value = "page") Integer page, @RequestParam(required = false, value = "limit") Integer limit) throws ApiException {
        if (allWords != null && !(allWords.equals("on") || allWords.equals("off"))) {
            allWords = "on";
        }
        if (query == null) query = "";
        if (allWords == null) allWords = "on";
        if (descriptionLength == null) descriptionLength = 200;
        return new ResponseEntity(productService.search(query, getPageable(page, limit), descriptionLength, allWords), HttpStatus.OK);

    }


    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "")
    public ResponseEntity getByProductId(@ApiParam(value = "", required = true) @PathVariable(value = "product_id") int id) throws ApiException {
        return new ResponseEntity(productService.getProduct(id), HttpStatus.OK);

    }

    @RequestMapping(value = "/products/inDepartment/{department_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "")
    public ResponseEntity getProductsByDepartment(@ApiParam(value = "", required = true) @PathVariable(value = "department_id") int id, @RequestParam(required = false, value = "description_length") Integer descriptionLength, @RequestParam(required = false, value = "page") Integer page, @RequestParam(required = false, value = "limit") Integer limit) throws ApiException {
        if (descriptionLength == null) descriptionLength = 200;
        return new ResponseEntity(productService.getProductsByDepartment(id, getPageable(page, limit), descriptionLength), HttpStatus.OK);

    }

    @RequestMapping(value = "/products/inCategory/{category_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "")
    public ResponseEntity getProductsBycategory(@ApiParam(value = "", required = true) @PathVariable(value = "category_id") int id, @RequestParam(required = false, value = "description_length") Integer descriptionLength, @RequestParam(required = false, value = "page") Integer page, @RequestParam(required = false, value = "limit") Integer limit) throws ApiException {
        if (descriptionLength == null) descriptionLength = 200;
        return new ResponseEntity(productService.getProductsByCategory(id, getPageable(page, limit), descriptionLength), HttpStatus.OK);

    }

    @RequestMapping(value = "/products/{product_id}/reviews", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "")
    public ResponseEntity getProductReviews(@ApiParam(value = "", required = true) @PathVariable(value = "product_id") int id) throws ApiException {
        return new ResponseEntity(reviewService.getReviews(id), HttpStatus.OK);

    }

    @RequestMapping(value = "/products/{product_id}/reviews", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Get attribute values of a product", notes = "")
    public ResponseEntity createReview(@Valid @RequestBody Review review,@ApiParam(value = "", required = true) @PathVariable(value = "product_id") int id) throws ApiException {
        review.setProductId(id);
        reviewService.createReview(review);
        return new ResponseEntity(HttpStatus.OK);

    }

    private Pageable getPageable(Integer page, Integer limit) throws ApiException {
        if (limit == null) limit = 20;
        if (page == null) page = 0;
        Pageable pageable = PageRequest.of(page, limit);
        return pageable;
    }
}
