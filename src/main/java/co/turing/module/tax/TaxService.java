package co.turing.module.tax;

import co.turing.module.tax.domain.Tax;

import java.util.List;

public interface TaxService {
    List<Tax> getTaxes();
    Tax getTax(int taxId);
}
