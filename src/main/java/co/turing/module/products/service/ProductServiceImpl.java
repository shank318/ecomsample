package co.turing.module.products.service;

import co.turing.dto.response.*;
import co.turing.module.categories.domain.Category;
import co.turing.module.products.ProductRepo;
import co.turing.module.products.domain.Product;
import co.turing.module.products.domain.ProductDepartment;
import co.turing.module.products.domain.ProductDetail;
import co.turing.module.user.domain.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames={"products"})
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all Products
     * @param pageable
     * @param length of description
     * @return
     */
    @Override
    @Cacheable
    public ProductsResponse getProducts(Pageable pageable, int length) {
        final Page<Product> all = productRepo.findAll(pageable);
        ProductsResponse searchResponse = new ProductsResponse();
        List<ProductDetail> detailList = new ArrayList<>();
        for (Product product : all.getContent()) {
            if (product.getDescription().length() > length) {
                product.setDescription(product.getDescription().substring(0, length) + "...");
            }
            detailList.add(modelMapper.map(product, ProductDetail.class));
        }
        searchResponse.setRows(detailList);
        searchResponse.setCount(all.getTotalElements());
        return searchResponse;
    }

    /**
     * Search products
     * @param query
     * @param pageable
     * @param length
     * @param allWords
     * @return
     */
    @Override
    @Cacheable
    public ProductSearchResponse search(String query, Pageable pageable, int length, String allWords) {
        query = "*"+query+"*";
        ProductSearchResponse searchResponse = new ProductSearchResponse();
        List<ProductSearchQueryResponse> responses;
        int count;
        if (allWords.equals("on")) {
            responses = productRepo.searchProductsAllWordOn(query, length, pageable.getPageNumber(), pageable.getPageSize());
            count = productRepo.countSearchResultsWordOn(query);
        } else {
            responses = productRepo.searchProductsAllWordOn(query, length, pageable.getPageNumber(), pageable.getPageSize());
            count = productRepo.countSearchResultsWordOff(query);
        }
        searchResponse.setCount(count);
        searchResponse.setRows(responses);
        return searchResponse;
    }

    /**
     * Get a product
     * @param productId
     * @return
     */
    @Override
    @Cacheable
    public Product getProduct(int productId) {
        return productRepo.findByProductId(productId);
    }

    /**
     *
     * @param catId
     * @param pageable
     * @param length
     * @return
     */
    @Override
    @Cacheable
    public CategoryProductsInfo getProductsByCategory(int catId, Pageable pageable, int length) {
        CategoryProductsInfo department = new CategoryProductsInfo();
        final List<ProductDepartment> productsFromDepartment = productRepo.getProductsFromCategories(catId, length, pageable.getPageNumber(), pageable.getPageSize());
        int count = productRepo.countProductsFromCategory(catId);
        department.setCount(count);
        department.setRows(productsFromDepartment);
        return department;
    }

    /**
     * Get products by department
     * @param depId
     * @param pageable
     * @param length
     * @return
     */
    @Override
    @Cacheable
    public DepartmentProducts getProductsByDepartment(int depId, Pageable pageable, int length) {
        DepartmentProducts department = new DepartmentProducts();
        final List<ProductDepartment> productsFromDepartment = productRepo.getProductsFromDepartment(depId, length, pageable.getPageNumber(), pageable.getPageSize());
        int count = productRepo.countProductsFromDepartment(depId);
        department.setCount(count);
        department.setRows(productsFromDepartment);
        return department;
    }

    /**
     * Get product details
     * @param productId
     * @return
     */
    @Override
    @Cacheable
    public ProductDetail getProductDetails(int productId) {
        final Product byProductId = productRepo.findByProductId(productId);
        ProductDetail productDetail = modelMapper.map(byProductId, ProductDetail.class);
        return productDetail;
    }
}
