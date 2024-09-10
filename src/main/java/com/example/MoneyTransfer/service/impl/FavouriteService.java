package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.dto.favouriteDto.CreateFavourite;
import com.example.MoneyTransfer.dto.favouriteDto.ResponseFavourite;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.custom.FavouriteNotFoundException;
import com.example.MoneyTransfer.model.Account;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.model.Favourite;
import com.example.MoneyTransfer.repository.AccountRepository;
import com.example.MoneyTransfer.repository.CustomerRepository;
import com.example.MoneyTransfer.repository.FavouriteRepository;
import com.example.MoneyTransfer.service.IFavourite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteService implements IFavourite {
    private final FavouriteRepository favouriteRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(FavouriteService.class);




    @Override
    @Transactional
    public Favourite addFavourite(CreateFavourite favourite) throws CustomerNotFoundException, AccountNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Logged-in customer not found"));

        // Find the favourite customer's account by account number
        Account favouriteAccount = accountRepository.findByAccountNumber(favourite.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Favourite account not found with number: " + favourite.getAccountNumber()));

        Customer favouriteCustomer = favouriteAccount.getCustomer();

        // Check if the favourite already exists
        if (favouriteRepository.existsByCustomerAndFavouriteCustomer(customer, favouriteCustomer)) {
            throw new IllegalStateException("This favourite already exists");
        }

        // Create and save the new Favourite
        Favourite newFavourite = Favourite.builder()
                .customer(customer)
                .favouriteCustomer(favouriteCustomer)
                .AccountNumber(favourite.getAccountNumber())
                .RecipientName(favourite.getRecipientName())
                .addedAt(LocalDateTime.now())
                .build();

        return favouriteRepository.save(newFavourite);
    }

    public List<ResponseFavourite> getAllFavourites() throws CustomerNotFoundException {
        // Get the logged-in customer from the token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Logged-in customer not found"));

        // Retrieve all Favourite entities associated with the logged-in customer
        List<Favourite> favourites = favouriteRepository.findByCustomer(customer);

        // Convert Favourite entities to ResponseFavourite DTOs
        return favourites.stream()
                .map(this::convertToResponseFavourite)
                .collect(Collectors.toList());
    }

    private ResponseFavourite convertToResponseFavourite(Favourite favourite) {
        ResponseFavourite responseFavourite = new ResponseFavourite();
        responseFavourite.setAccountNumber(favourite.getAccountNumber());
        responseFavourite.setRecipientName(favourite.getRecipientName());
        return responseFavourite;
    }

    @Override
    public void deleteFavouriteById(Long id) throws FavouriteNotFoundException, FavouriteNotFoundException, CustomerNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Logged-in customer not found"));

        Favourite favourite = favouriteRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite not found with id: " + id));

        // Check if the favourite belongs to the logged-in customer
        if (!favourite.getCustomer().getId().equals(customer.getId())) {
            throw new FavouriteNotFoundException("Favourite not found for the logged-in customer");
        }

        favouriteRepository.delete(favourite);
        logger.info("Deleted favourite with id: {}", id);
    }




}