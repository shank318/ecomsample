package co.turing.module.payment;

import co.turing.dto.request.CreateOrder;
import co.turing.dto.request.PaymentRequest;
import co.turing.error.ApiException;
import co.turing.module.order.domain.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
@Api(tags = {"Stripe Payment controller"})
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "/stripe/charge", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Create a stripe payment .", notes = "")
    public ResponseEntity createPayment(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PaymentRequest paymentRequest) throws ApiException, StripeException {
        log.info("Payment request -->" + paymentRequest.toString());
        paymentRequest.setCustomerId(Integer.parseInt(userDetails.getUsername()));
        return new ResponseEntity(paymentService.chargePayment(paymentRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/stripe/webhooks", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Stripe Webhook .", notes = "")
    public ResponseEntity stripeWebhook(HttpServletRequest request) throws ApiException, IOException, StripeException {
        final String payload = IOUtils.toString(request.getInputStream());
        log.info("Webhook received request -->" + payload.toString());
        return new ResponseEntity(paymentService.confirmPayment(payload,request.getHeader("Stripe-Signature")), HttpStatus.OK);
    }

}
