package co.turing.module.payment;

import co.turing.dto.request.PaymentRequest;
import com.stripe.exception.StripeException;

public interface PaymentService<T> {
    T chargePayment(PaymentRequest paymentRequest) throws StripeException;
    boolean confirmPayment(T t);

}
