package com.example.MoneyTransfer.repository;

import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    boolean existsByCustomerAndFavouriteCustomer(Customer customer, Customer favourite);

    List<Favourite> findByCustomer(Customer customer);

}
