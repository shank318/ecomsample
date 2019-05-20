package co.turing.module.tax;

import co.turing.module.tax.domain.Tax;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepo extends JpaRepository<Tax, Integer> {
    Tax findByTaxId(int taxId);
}
