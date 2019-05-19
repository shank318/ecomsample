package co.turing.module.categories;

import co.turing.module.categories.domain.Category;
import co.turing.module.department.domian.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CategoriesRepository extends PagingAndSortingRepository<Category, Integer> {

    Category findByCategoryId(int id);

    List<Category> findAllByDepartmentId(int departmentId);


}
