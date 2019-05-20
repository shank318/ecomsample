package co.turing.module.payment;

import co.turing.dto.request.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api-key}")
    private String stripeApiKey;


    @Override
    public Object chargePayment(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", paymentRequest.getAmount());
        chargeMap.put("currency", paymentRequest.getCurrency());
        chargeMap.put("source", "tok_1234"); // obtained via Stripe.js
        chargeMap.put("metadata", paymentRequest.getOrderId());
        Charge charge = Charge.create(chargeMap);
        return charge;
    }
}
