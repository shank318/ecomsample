package co.turing.dto.response;

import co.turing.module.products.domain.ProductDepartment;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentProducts {
    List<ProductDepartment> rows;
    int count;
}
