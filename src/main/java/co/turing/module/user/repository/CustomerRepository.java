package co.turing.module.user.repository;

import co.turing.module.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    Customer findByEmail(String email);


}
