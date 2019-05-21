package co.turing.module.payment;

import co.turing.dto.request.PaymentRequest;
import co.turing.module.order.OrderService;
import co.turing.module.payment.domain.PaymentStatus;
import co.turing.module.payment.domain.StripeStatus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class StripeServiceImpl implements PaymentService<Charge> {

    @Value("${stripe.api-key}")
    private String stripeApiKey;

    @Autowired
    OrderService orderService;


    @Override
    public Charge chargePayment(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", paymentRequest.getAmount());
        chargeMap.put("currency", paymentRequest.getCurrency());
        chargeMap.put("source", paymentRequest.getStripeToken()); // obtained via Stripe.js
        chargeMap.put("order", paymentRequest.getOrderId());
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("order_id", paymentRequest.getOrderId());
        chargeMap.put("metadata", metadata);
        chargeMap.put("customer", paymentRequest.getCustomerId());
        Charge charge = Charge.create(chargeMap);
        return charge;
    }

    @Override
    public boolean confirmPayment(Charge charge) {
        final StripeStatus stripeStatus = StripeStatus.valueOf(charge.getStatus().toUpperCase());
        return orderService.updateOrderStatus(Integer.parseInt(charge.getOrder()), stripeStatus.getValue());
    }
}
