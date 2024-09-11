package com.example.MoneyTransfer.controller;


import com.example.MoneyTransfer.dto.favouriteDto.CreateFavourite;
import com.example.MoneyTransfer.dto.favouriteDto.ResponseFavourite;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.custom.FavouriteNotFoundException;
import com.example.MoneyTransfer.exception.response.ErrorDetails;
import com.example.MoneyTransfer.model.Favourite;
import com.example.MoneyTransfer.service.IFavourite;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/favourites")
@RequiredArgsConstructor
@Validated
@Tag(name = "Favourite Controller ", description = "Favourite Controller ")

public class FavouriteController {

    private final IFavourite favouriteService;


    @Operation(summary = "Add Customer To Favorite")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CreateFavourite.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = CustomerNotFoundException.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = AuthenticationException.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = DataIntegrityViolationException.class), mediaType = "application/json")})
    @PostMapping
    public ResponseEntity<Favourite> addFavourite(@RequestBody CreateFavourite createFavourite) {
        try {
            Favourite newFavourite = favouriteService.addFavourite(createFavourite);
            return new ResponseEntity<>(newFavourite, HttpStatus.CREATED);
        } catch (CustomerNotFoundException | AccountNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }



    @Operation(summary = "Get All Favourites for the logged-in customer")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Favourite.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "User Not Found ", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping

    public ResponseEntity<List<ResponseFavourite>> getAllFavourites() {
        try {
            List<ResponseFavourite> favourites = favouriteService.getAllFavourites();
            return ResponseEntity.ok(favourites);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Delete From Favorites")
    @ApiResponse(responseCode = "200",description = "this favourite successfully deleted")
    @ApiResponse(responseCode = "404", description = "Customer Not Found ", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "FavouriteNotFoundException", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) {
        try {
            favouriteService.deleteFavouriteById(id);
            return ResponseEntity.noContent().build();
        } catch (FavouriteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
