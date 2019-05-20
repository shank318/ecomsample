package co.turing.module.shipping;

import co.turing.module.shipping.domain.Shipping;

import java.util.List;

public interface ShippingService {
    List<Shipping> getAllShippingDetails();
    Shipping getShippingRegion(int taxId);
}
