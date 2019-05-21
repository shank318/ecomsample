package co.turing.security;

import co.turing.module.user.domain.Customer;
import co.turing.module.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        final Customer user = customerRepository.findByCustomerId(Integer.parseInt(customerId));

        if (user == null) {
            throw new UsernameNotFoundException("Customer '" + customerId + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(customerId)//
                .password(user.getPassword())//
                .accountExpired(false)//
                .accountLocked(false)//
                .authorities(new ArrayList<>())
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }



}
