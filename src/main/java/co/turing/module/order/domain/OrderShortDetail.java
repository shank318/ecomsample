package co.turing.module.order.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class OrderShortDetail {
    private int orderId;
    private double totalAmount;
    LocalDateTime createdOn;
    LocalDateTime shippedOn;
    private int status;
}
