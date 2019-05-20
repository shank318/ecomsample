package co.turing.module.order.domain;

import lombok.Getter;

@Getter
public enum  OrderStatus {

    CREATED("created",1),
    SHIPPED("shipped",2),
    DELIVERED("created",3);

    private String status;

    OrderStatus(String status, int value) {
        this.status = status;
        this.value = value;
    }

    private int value;


}
