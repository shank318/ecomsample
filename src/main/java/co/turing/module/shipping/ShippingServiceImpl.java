package co.turing.module.shipping;

import co.turing.module.shipping.domain.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingRepo shippingRepo;

    @Override
    public List<Shipping> getAllShippingDetails() {
        return shippingRepo.findAll();
    }

    @Override
    public Shipping getShippingRegion(int shippingID) {
        return shippingRepo.findByShippingRegionId(shippingID);
    }
}
