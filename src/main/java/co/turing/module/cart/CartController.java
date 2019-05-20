package co.turing.module.cart;


import co.turing.dto.request.AddCartItem;
import co.turing.dto.request.UpdateCart;
import co.turing.dto.response.CartTotalAmount;
import co.turing.dto.response.GenerateCartId;
import co.turing.error.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Cart controller"})
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/shoppingcart/generateUniqueId", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity getCartId() throws ApiException {
        return new ResponseEntity(new GenerateCartId(cartService.generateCartId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/add", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity addToCart(@Valid @RequestBody AddCartItem addCartItem) throws ApiException {
        return new ResponseEntity(cartService.addCartIten(addCartItem), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/{cart_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity getCartId(@ApiParam(value = "", required = true) @PathVariable(value = "cart_id") String id) throws ApiException {
        return new ResponseEntity(cartService.getCart(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/update/{item_id}", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity getCartId(@Valid @RequestBody UpdateCart updateCart, @ApiParam(value = "", required = true) @PathVariable(value = "item_id") int id) throws ApiException {
        updateCart.setItemId(id);
        return new ResponseEntity(cartService.updateCart(updateCart), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/empty/{cart_id}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity emptyCart(@ApiParam(value = "", required = true) @PathVariable(value = "cart_id") String id) throws ApiException {
        return new ResponseEntity(cartService.deleteCart(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/removeProduct/{item_id}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity removeItemInCart(@ApiParam(value = "", required = true) @PathVariable(value = "item_id") int id) throws ApiException {
        return new ResponseEntity(cartService.removeCartItem(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/shoppingcart/totalAmount/{cart_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get all Categories .", notes = "")
    public ResponseEntity getTotalAmountCart(@ApiParam(value = "", required = true) @PathVariable(value = "cart_id") String id) throws ApiException {
        return new ResponseEntity(new CartTotalAmount(cartService.totalCartAmount(id)), HttpStatus.OK);
    }

}
