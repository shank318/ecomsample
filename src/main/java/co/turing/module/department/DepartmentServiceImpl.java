package co.turing.module.department;

import co.turing.error.ApiException;
import co.turing.error.TuringErrors;
import co.turing.module.department.domian.Department;
import co.turing.module.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames={"department"})
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    @Cacheable
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    /**
     * Get department
     * @param departmentId
     * @return
     */
    @Override
    @Cacheable
    public Department getDepartment(int departmentId) {
        final Department byDepartmentId = departmentRepository.findByDepartmentId(departmentId);
        if (byDepartmentId == null) {
            throw new ApiException(TuringErrors.DEP_NOT_FOUND.getMessage(), TuringErrors.DEP_NOT_FOUND.getCode(), TuringErrors.DEP_NOT_FOUND.getField());
        }
        return byDepartmentId;
    }
}
