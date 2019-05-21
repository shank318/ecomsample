package co.turing.module.payment.domain;

import lombok.Getter;

@Getter
public enum StripeStatus {

    SUCCEEDED("succeeded", PaymentStatus.PAID),
    FAILED("failed", PaymentStatus.FAILED),
    PENDING("pending", PaymentStatus.PENDING),
    CAPTURED("captured", PaymentStatus.CAPTURED),
    REFUNDED("refunded", PaymentStatus.REFUNDED);

    private String status;

    StripeStatus(String status, PaymentStatus value) {
        this.status = status;
        this.value = value;
    }

    private PaymentStatus value;

}
