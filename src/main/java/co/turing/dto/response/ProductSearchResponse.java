package co.turing.dto.response;

import co.turing.module.products.domain.Product;
import co.turing.module.products.domain.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchResponse {
    List<ProductSearchQueryResponse> rows;
    long count;
}
