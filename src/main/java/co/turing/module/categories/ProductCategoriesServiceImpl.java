package co.turing.module.categories;

import co.turing.dto.response.ProductCategoriesInfo;
import co.turing.module.categories.domain.ProductCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {
    @Autowired
    ProductCategorieRepo productCategorieRepo;

    /**
     * Get ALl categories by
     * product id
     * @param productId
     * @return
     */
    @Override
    public List<ProductCategoriesInfo> findCategories(int productId) {
        return productCategorieRepo.findAllByProductId(productId);
    }
}
