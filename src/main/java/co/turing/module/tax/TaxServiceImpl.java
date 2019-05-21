package co.turing.module.tax;

import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.tax.domain.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames={"tax"})
public class TaxServiceImpl implements TaxService {

    @Autowired
    TaxRepo taxRepo;

    @Override
    @Cacheable
    public List<Tax> getTaxes() {
        return taxRepo.findAll();
    }

    /**
     *
     * @param taxId
     * @return
     */
    @Override
    @Cacheable
    public Tax getTax(int taxId) {
        final Tax byTaxId = taxRepo.findByTaxId(taxId);
        if (byTaxId==null){
            throw new ApiException(TuringErrors.TAX_NOT_FOUND.getMessage(), TuringErrors.TAX_NOT_FOUND.getCode(), TuringErrors.TAX_NOT_FOUND.getField());
        }
        return taxRepo.findByTaxId(taxId);
    }
}
