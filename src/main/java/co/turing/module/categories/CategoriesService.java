package co.turing.module.categories;

import co.turing.dto.response.ListCategoriesResponse;
import co.turing.module.categories.domain.Category;
import co.turing.module.department.domian.Department;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriesService {
    ListCategoriesResponse findAllCategories(Pageable pageable);

    Category getCategory(int id);

    List<Category> findCategoriesByDepartmentId(int departmentId);
}
