package co.turing.module.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum PaymentStatusStateMachine {
    INIT("initiated", 0) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(CAPTURED, PAID, FAILED, PENDING);
        }
    },
    PAID("paid", 1) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(REFUNDED);
        }
    },
    FAILED("failed", 2) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(FAILED);
        }
    },
    PENDING("pending", 3) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(PAID, FAILED);
        }
    },
    CAPTURED("captured", 4) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(PAID, FAILED);
        }
    },
    REFUNDED("refunded", 5) {
        @Override
        public List<PaymentStatusStateMachine> nextState() {
            return Arrays.asList(REFUNDED);
        }
    };

    private String status;

    PaymentStatusStateMachine(String status, int value) {
        this.status = status;
        this.value = value;
    }

    private int value;

    public abstract List<PaymentStatusStateMachine> nextState();

    public static PaymentStatusStateMachine getEnumByString(int value) {
        for (PaymentStatusStateMachine e : PaymentStatusStateMachine.values()) {
            if (value == e.getValue()) return e;
        }
        return null;
    }
}