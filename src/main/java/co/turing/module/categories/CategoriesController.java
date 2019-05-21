package co.turing.module.categories;


import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
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

import javax.ws.rs.QueryParam;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Categories controller"})
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    ProductCategoriesService productCategoriesService;

    /**
     *
     * @param order
     * @param page
     * @param limit
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity getAllCategories(@RequestParam(required = false, value = "order") String order, @RequestParam(required = false, value = "page") Integer page, @RequestParam(required = false, value = "limit") Integer limit) throws ApiException {
        return new ResponseEntity(categoriesService.findAllCategories(getPageable(order, page, limit)), HttpStatus.OK);

    }

    /**
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/categories/{category_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get a category", notes = "")
    public ResponseEntity getCategoryById(@ApiParam(value = "", required = true) @PathVariable(value = "category_id") int id) throws ApiException {
        return new ResponseEntity(categoriesService.getCategory(id), HttpStatus.OK);

    }

    /**
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/categories/inProduct/{product_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get categories of a product", notes = "")
    public ResponseEntity getCategoriesByProductId(@ApiParam(value = "", required = true) @PathVariable(value = "product_id") int id) throws ApiException {
        return new ResponseEntity(productCategoriesService.findCategories(id), HttpStatus.OK);

    }

    /**
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/categories/inDepartment/{department_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get categories of a department", notes = "")
    public ResponseEntity getCategoriesByDepartmentId(@ApiParam(value = "", required = true) @PathVariable(value = "department_id") int id) throws ApiException {
        return new ResponseEntity(categoriesService.findCategoriesByDepartmentId(id), HttpStatus.OK);

    }

    /**
     *
     * @param order
     * @param page
     * @param limit
     * @return
     * @throws ApiException
     */
    private Pageable getPageable(String order, Integer page, Integer limit) throws ApiException {
        if (limit == null) limit = 20;
        if (page == null) page = 0;
        Pageable pageable = null;
        if (order == null) {
            pageable = PageRequest.of(page, limit);
        } else {
            final String[] split = order.split(",");
            if (split.length == 2) {
                if (!(split[0].equals("category_id") || split[0].equals("name"))) {
                    throw new ApiException(TuringErrors.PAG_02.getMessage(), TuringErrors.PAG_02.getCode(), "order");
                }
                if (!(split[1].equals("DESC") || split[1].equals("ASC"))) {
                    throw new ApiException(TuringErrors.PAG_01.getMessage(), TuringErrors.PAG_01.getCode(), "order");
                }
                if (split[1].equals("DESC")) {
                    pageable = PageRequest.of(page, limit, Sort.by(split[0]).descending());
                } else {
                    pageable = PageRequest.of(page, limit, Sort.by(split[0]).ascending());
                }
            } else {
                throw new ApiException(TuringErrors.PAG_01.getMessage(), TuringErrors.PAG_01.getCode(), TuringErrors.PAG_01.getField());
            }
        }
        return pageable;
    }

}
