package co.turing.module.order;


import co.turing.dto.request.CreateOrder;
import co.turing.dto.response.GenerateCartId;
import co.turing.dto.response.OrderInfo;
import co.turing.error.ApiException;
import co.turing.module.attributes.domain.AttributeValue;
import co.turing.module.cart.CartService;
import co.turing.module.order.domain.Order;
import co.turing.module.order.domain.OrderShortDetail;
import co.turing.module.user.domain.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Order controller"})
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/orders/{order_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get an Order .", notes = "", response = OrderInfo.class)
    public ResponseEntity getOrderById(@ApiParam(value = "", required = true) @PathVariable(value = "order_id") int id) throws ApiException {
        log.info("Get Order request -->" + id);
        return new ResponseEntity(orderService.getOrder(id), HttpStatus.OK);
    }

    /**
     *
     * @param userDetails
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/orders/inCustomer", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get an Order .", notes = "", response = Order.class,
            responseContainer = "List")
    public ResponseEntity getOrdersByCustomerId(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) throws ApiException {
        return new ResponseEntity(orderService.getOrders(Integer.parseInt(userDetails.getUsername())), HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/orders/shortDetail/{order_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get Order details in short .", notes = "", response = OrderShortDetail.class)
    public ResponseEntity getOrderShortDetail(@ApiParam(value = "", required = true) @PathVariable(value = "order_id") int id) throws ApiException {
        return new ResponseEntity(orderService.getShortDetailOrder(id), HttpStatus.OK);
    }

    /**
     *
     * @param userDetails
     * @param createOrder
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/orders", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Create an order .", notes = "")
    public ResponseEntity createOrder(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreateOrder createOrder) throws ApiException {
        log.info("Create order request -->" + createOrder.toString());
        Order order = new Order();
        order.setReference(createOrder.getCartId());
        order.setTaxId(createOrder.getTaxId());
        order.setShippingId(createOrder.getShippingId());
        order.setReference(createOrder.getCartId());
        order.setCustomerId(Integer.parseInt(userDetails.getUsername()));
        Map<String, Integer> output = new HashMap<>();
        output.put("order_id", orderService.createOrder(order).getOrderId());
        return new ResponseEntity(output, HttpStatus.OK);
    }

}
