package co.turing.module.categories;

import co.turing.dto.response.ListCategoriesResponse;
import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.categories.domain.Category;
import co.turing.module.department.domian.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Override
    public ListCategoriesResponse findAllCategories(Pageable pageable) {
        final Page<Category> all = categoriesRepository.findAll(pageable);
        ListCategoriesResponse categoriesResponse = new ListCategoriesResponse();
        categoriesResponse.setRows(all.getContent());
        categoriesResponse.setCount(all.getTotalElements());
        return categoriesResponse;
    }

    @Override
    public Category getCategory(int id) {
        final Category byCategoryId = categoriesRepository.findByCategoryId(id);
        if (byCategoryId == null) {
            throw new ApiException(TuringErrors.CAT_NOT_FOUND.getMessage(), TuringErrors.CAT_NOT_FOUND.getCode(), TuringErrors.CAT_NOT_FOUND.getField());
        }
        return byCategoryId;
    }

    @Override
    public List<Category> findCategoriesByDepartmentId(int departmentId) {
        return categoriesRepository.findAllByDepartmentId(departmentId);
    }
}
