package com.example.MoneyTransfer.security;

import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findCustomerByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found with email: " + username));

        return CustomerDetailsImpl.builder()
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();
    }
}
