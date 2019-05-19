package co.turing.dto.response;

import co.turing.module.products.domain.ProductDepartment;
import co.turing.module.products.domain.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class CategoryProductsInfo {
    List<ProductDepartment> rows;
    int count;
}
