package co.turing.dto.response;

public interface ProductSearchQueryResponse {


    int getProductId();

    String getName();

    String getDescription() ;

    double getPrice();

    String getThumbnail();

    double getDiscountedPrice();


}
