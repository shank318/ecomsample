package co.turing.dto.response;

import co.turing.module.products.domain.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductsResponse {
    List<ProductDetail> rows;
    long count;
}
