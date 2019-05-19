package co.turing.module.categories;

import co.turing.dto.response.ProductCategoriesInfo;
import co.turing.module.categories.domain.ProductCategories;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductCategoriesService {
    List<ProductCategoriesInfo> findCategories(int productId);
}
