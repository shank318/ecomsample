package co.turing.dto.response;

import co.turing.module.categories.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class ListCategoriesResponse {
    List<Category> rows;
    long count;
}
