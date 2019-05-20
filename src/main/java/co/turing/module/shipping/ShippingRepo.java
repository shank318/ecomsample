package co.turing.module.shipping;

import co.turing.module.shipping.domain.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepo extends JpaRepository<Shipping, Integer> {
    Shipping findByShippingRegionId(int taxId);
}
