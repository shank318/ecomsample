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

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        final Customer user = customerRepository.findByEmail(emailId);

        if (user == null) {
            throw new UsernameNotFoundException("Email '" + emailId + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(String.valueOf(user.getCustomerId()))//
                .password(user.getPassword())//
                .accountExpired(false)//
                .accountLocked(false)//
                .authorities(new ArrayList<>())
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }



}
