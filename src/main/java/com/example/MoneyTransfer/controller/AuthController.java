package com.example.MoneyTransfer.controller;

import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
import com.example.MoneyTransfer.dto.UpdatePasswordDTO;
import com.example.MoneyTransfer.dto.authDto.LoginRequestDTO;
import com.example.MoneyTransfer.dto.authDto.LoginResponseDTO;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerRequest;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerResponse;
import com.example.MoneyTransfer.exception.custom.CustomerAlreadyExistsException;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.response.ErrorDetails;
import com.example.MoneyTransfer.security.JwtUtils;
import com.example.MoneyTransfer.security.TokenBlacklistService;
import com.example.MoneyTransfer.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Customer Auth Controller", description = "Customer Auth controller")
public class AuthController {


    private final IAuthService authService;

    private  final TokenBlacklistService tokenBlacklistService;

    @CrossOrigin
    @PostMapping("/register")
    @Operation(summary = "Account Creation")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegisterCustomerResponse.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    public RegisterCustomerResponse register(@RequestBody @Valid RegisterCustomerRequest customer ) throws CustomerAlreadyExistsException {

        return this.authService.register(customer);
    }
    @CrossOrigin
    @Operation(summary = "Login and generate JWT")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws CustomerNotFoundException {
        return this.authService.login(loginRequestDTO);
    }


    @CrossOrigin
    @PostMapping("/logout")
    @Operation(summary = "user logout")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CustomerDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String tokenAuthHeader) {
        Map<String, String> response = new HashMap<>();

        if (tokenAuthHeader == null || !tokenAuthHeader.startsWith("Bearer ")) {
            response.put("message", "Invalid or missing tokenAuth header");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String token = tokenAuthHeader.substring(7);

        if (token.isEmpty() || tokenBlacklistService.isTokenBlacklisted(token)) {
            response.put("message", "Invalid or already logged out token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        tokenBlacklistService.blacklistToken(token);

        // Clear SecurityContext
        SecurityContextHolder.clearContext();

        response.put("message", "Logout successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @CrossOrigin
    @Operation(summary = "Update Customer Password")
    @ApiResponse(responseCode = "200", description = "Password updated successfully",
            content = {@Content(schema = @Schema(type = "string"), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {

        try {
                authService.updatePassword(updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
                return ResponseEntity.ok("Password updated successfully");
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Old password is incorrect", HttpStatus.BAD_REQUEST);
            } catch (CustomerNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


