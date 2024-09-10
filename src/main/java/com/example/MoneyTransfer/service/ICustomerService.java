package com.example.MoneyTransfer.service;

import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.LastCustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerRequest;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerResponse;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;

import java.util.List;

public interface ICustomerService {

    /**
     * @param email
     * @return
     * @throws CustomerNotFoundException
     */
    CustomerDto getCustomerByEmail(String email) throws CustomerNotFoundException;

    CustomerDto getCustomerById(int id) throws CustomerNotFoundException;


    List<CustomerDto> getAllCustomers();

    LastCustomerDto getLastCustomerAdded();


    UpdateCustomerResponse updateCustomer(String email, UpdateCustomerRequest customerDto) throws CustomerNotFoundException;

    void deleteCustomer() throws CustomerNotFoundException;

}
