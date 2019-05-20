package co.turing.module.department;

import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.department.domian.Department;
import co.turing.module.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartment(int departmentId) {
        final Department byDepartmentId = departmentRepository.findByDepartmentId(departmentId);
        if (byDepartmentId == null) {
            throw new ApiException(TuringErrors.DEP_NOT_FOUND.getMessage(), TuringErrors.DEP_NOT_FOUND.getCode(), TuringErrors.DEP_NOT_FOUND.getField());
        }
        return byDepartmentId;
    }
}
