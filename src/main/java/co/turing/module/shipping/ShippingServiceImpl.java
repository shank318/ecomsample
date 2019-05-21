package co.turing.module.shipping;

import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.shipping.domain.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames={"shipping"})
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingRepo shippingRepo;

    @Override
    @Cacheable
    public List<Shipping> getAllShippingDetails() {
        return shippingRepo.findAll();
    }

    /**
     *
     * @param shippingID
     * @return
     */
    @Override
    @Cacheable
    public Shipping getShippingRegion(int shippingID) {
        final Shipping byShippingRegionId = shippingRepo.findByShippingRegionId(shippingID);
        if (byShippingRegionId==null){
            throw new ApiException(TuringErrors.SHIPPING_NOT_FOUND.getMessage(), TuringErrors.SHIPPING_NOT_FOUND.getCode(), TuringErrors.SHIPPING_NOT_FOUND.getField());
        }
        return byShippingRegionId;
    }
}
