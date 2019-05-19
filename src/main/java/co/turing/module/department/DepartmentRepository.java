package co.turing.module.department;

import co.turing.module.department.domian.Department;
import co.turing.module.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAll();

    Department findByDepartmentId(int departmentId);


}
