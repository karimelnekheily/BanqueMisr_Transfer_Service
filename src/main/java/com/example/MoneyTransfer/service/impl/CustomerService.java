package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.LastCustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerRequest;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerResponse;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.repository.CustomerRepository;
import com.example.MoneyTransfer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;


    @Override
    public CustomerDto getCustomerByEmail(String email) throws CustomerNotFoundException {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email))
                .toDTO();
    }

    @Override
    public CustomerDto getCustomerById(int id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id))
                .toDTO();
    }


    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(Customer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LastCustomerDto getLastCustomerAdded() {
       return this.customerRepository.findFirstByOrderByCreatedAtDesc();
    }

    @Override
    public UpdateCustomerResponse updateCustomer(String email, UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException {
         Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));

         customer.setName(updateCustomerRequest.getName());
         customer.setEmail(updateCustomerRequest.getEmail());
         customer.setPhoneNumber(updateCustomerRequest.getPhoneNumber());

         Customer updatedCustomer = customerRepository.save(customer);

        return UpdateCustomerResponse.builder()
                .updatedAt(LocalDateTime.now())
                .massage("Customer updated successfully")
                .details("Customer details updated for email: " + updatedCustomer.getEmail())
                .name(updatedCustomer.getName())
                .email(updatedCustomer.getEmail())
                .phoneNumber(updatedCustomer.getPhoneNumber())
                .httpStatusCode(HttpStatus.OK)
                .build();
    }

    @Override
    @Transactional
    public void deleteCustomer() throws CustomerNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming email is used as the username

        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));

        customerRepository.delete(customer);
    }


}
