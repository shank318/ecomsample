package co.turing.module.payment.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    INIT("initiated",0),
    PAID("paid",1),
    FAILED("failed",2),
    PENDING("pending",3),
    CAPTURED("captured",4),
    REFUNDED("refunded",5);

    private String status;

    PaymentStatus(String status, int value) {
        this.status = status;
        this.value = value;
    }

    private int value;


}
