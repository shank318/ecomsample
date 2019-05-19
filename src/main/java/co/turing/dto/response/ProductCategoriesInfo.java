package co.turing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface ProductCategoriesInfo {


    int getCategory_id();

    String getName();

    int getDepartment_id();

}
