package co.turing.module.products.service;

import co.turing.dto.response.CategoryProductsInfo;
import co.turing.dto.response.DepartmentProducts;
import co.turing.dto.response.ProductSearchResponse;
import co.turing.dto.response.ProductsResponse;
import co.turing.module.products.domain.Product;
import co.turing.module.products.domain.ProductDetail;
import co.turing.module.products.domain.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductsResponse getProducts(Pageable pageable, int length);
    ProductSearchResponse search(String query,Pageable pageable, int length,String allWords);
    Product getProduct(int productId);
    CategoryProductsInfo getProductsByCategory(int catId, Pageable pageable, int length);
    DepartmentProducts getProductsByDepartment(int depId, Pageable pageable, int length);
    ProductDetail getProductDetails(int productId);


}
