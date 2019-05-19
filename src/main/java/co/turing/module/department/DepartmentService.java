package co.turing.module.department;


import co.turing.dto.request.CustomerLoginDto;
import co.turing.dto.response.CustomerAuthResponse;
import co.turing.module.department.domian.Department;
import co.turing.module.user.domain.Customer;

import java.util.List;

public interface DepartmentService {


     List<Department> getAll();
     Department getDepartment(int departmentId);

}
