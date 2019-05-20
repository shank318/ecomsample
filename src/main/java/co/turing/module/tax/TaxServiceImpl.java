package co.turing.module.tax;

import co.turing.module.tax.domain.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    TaxRepo taxRepo;

    @Override
    public List<Tax> getTaxes() {
        return taxRepo.findAll();
    }

    @Override
    public Tax getTax(int taxId) {
        return taxRepo.findByTaxId(taxId);
    }
}
