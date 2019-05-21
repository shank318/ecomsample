package co.turing.module.payment.domain;

import co.turing.module.payment.PaymentStatusStateMachine;
import lombok.Getter;

@Getter
public enum StripeStatus {

    SUCCEEDED("charge.succeeded", PaymentStatusStateMachine.PAID),
    FAILED("charge.failed", PaymentStatusStateMachine.FAILED),
    PENDING("charge.pending", PaymentStatusStateMachine.PENDING),
    CAPTURED("charge.captured", PaymentStatusStateMachine.CAPTURED),
    REFUNDED("charge.refunded", PaymentStatusStateMachine.REFUNDED);

    private String status;

    StripeStatus(String status, PaymentStatusStateMachine value) {
        this.status = status;
        this.value = value;
    }

    private PaymentStatusStateMachine value;

    public static StripeStatus getEnumByString(String status) {
        for (StripeStatus e : StripeStatus.values()) {
            if (status == e.getStatus()) return e;
        }
        return null;
    }

}
