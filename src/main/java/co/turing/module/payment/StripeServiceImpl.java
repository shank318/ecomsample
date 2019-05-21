package co.turing.module.payment;

import co.turing.dto.request.PaymentRequest;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.order.OrderService;
import co.turing.module.payment.domain.StripeStatus;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class StripeServiceImpl implements PaymentService<Charge> {

    @Value("${stripe.api-key}")
    private String stripeApiKey;

    @Value("${stripe.secret}")
    private String webhookSecret;

    @Autowired
    OrderService orderService;

    /**
     * @param paymentRequest
     * @return
     * @throws StripeException
     */
    @Override
    public Charge chargePayment(PaymentRequest paymentRequest) throws StripeException {
        log.info("Creating payment for Order ID::" + paymentRequest.getOrderId());
        Stripe.apiKey = stripeApiKey;
        if (paymentRequest.getCurrency() == null || !paymentRequest.getCurrency().toLowerCase().equals("USD")) {
            paymentRequest.setCurrency("USD");
        }
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", paymentRequest.getAmount());
        chargeMap.put("currency", paymentRequest.getCurrency());
        chargeMap.put("source", paymentRequest.getStripeToken()); // obtained via Stripe.js
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("order_id", paymentRequest.getOrderId());
        chargeMap.put("metadata", metadata);
        Charge charge = Charge.create(chargeMap);
        charge.setLastResponse(null);
        return charge;
    }

    /**
     * @param reqBody
     * @param signature
     * @return
     * @throws SignatureVerificationException
     */
    @Override
    public boolean confirmPayment(String reqBody, String signature) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(
                reqBody, signature, webhookSecret
        );
        log.info("Stripe Event received::" + event.getType());
        final StripeStatus stripeStatus = StripeStatus.getEnumByString(event.getType().toLowerCase());
        if (stripeStatus == null) {
            throw new ApiException(TuringErrors.INVALID_ORDER_STATUS.getMessage(), TuringErrors.INVALID_ORDER_STATUS.getCode(), TuringErrors.INVALID_ORDER_STATUS.getField());
        }
        final EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        final Optional<StripeObject> object = dataObjectDeserializer.getObject();
        if (!object.isPresent()) {
            return false;
        }
        Charge charge = (Charge) object.get();
        if (!charge.getMetadata().containsKey("order_id")){
            return true;
        }
        orderService.updateOrderStatus(Integer.parseInt(charge.getMetadata().get("order_id")), stripeStatus.getValue());
        return true;
    }

}
