package co.turing.module.products.domain;

import lombok.Data;

public interface ProductDepartment {


    int getProductId();

     String getName();

     String getDescription();

     double getPrice();

     String getThumbnail();

     double getDiscountedPrice();


}
