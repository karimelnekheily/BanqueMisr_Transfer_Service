package com.example.MoneyTransfer.service;

import com.example.MoneyTransfer.dto.favouriteDto.CreateFavourite;
import com.example.MoneyTransfer.dto.favouriteDto.ResponseFavourite;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.custom.FavouriteNotFoundException;
import com.example.MoneyTransfer.model.Favourite;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface IFavourite {

    Favourite addFavourite(CreateFavourite  favourite) throws CustomerNotFoundException, AccountNotFoundException;

    List<ResponseFavourite> getAllFavourites() throws CustomerNotFoundException;

    void deleteFavouriteById( Long id) throws CustomerNotFoundException, FavouriteNotFoundException;
}
