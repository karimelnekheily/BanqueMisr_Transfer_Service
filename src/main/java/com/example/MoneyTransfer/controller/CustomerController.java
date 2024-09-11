package com.example.MoneyTransfer.controller;


import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.LastCustomerDto;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerRequest;
import com.example.MoneyTransfer.dto.CustomerDto.UpdateCustomerResponse;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.response.ErrorDetails;
import com.example.MoneyTransfer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Validated
@Tag(name = "Customer Controller", description = "Customer controller")
public class CustomerController {

private  final ICustomerService customerService;
    @CrossOrigin
    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CustomerDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {
        return this.customerService.getCustomerById(Math.toIntExact(customerId));
    }

    @CrossOrigin(origins = "*")
    @Operation(summary = "Get customer by email")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CustomerDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable String email) throws CustomerNotFoundException {
        CustomerDto customerDto = this.customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customerDto);
    }

    @CrossOrigin
    @Operation(summary = "Get all customers")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CustomerDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping
    public List<CustomerDto> getAllCustomers()  {
        return this.customerService.getAllCustomers();
    }
    @CrossOrigin
    @Operation(summary = "Update customer by email")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UpdateCustomerResponse.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PutMapping("/update")
    public UpdateCustomerResponse updateCustomer(@RequestParam String email, @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException {

        return this.customerService.updateCustomer(email,updateCustomerRequest);
    }

    @CrossOrigin
    @Operation(summary = "Get the last customer added")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LastCustomerDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/last-added")
    public ResponseEntity<LastCustomerDto> getLastCustomerAdded() {
        LastCustomerDto lastCustomer = customerService.getLastCustomerAdded();
        return new ResponseEntity<>(lastCustomer, HttpStatus.OK);
    }
    @CrossOrigin
    @Operation(summary = "Delete the current customer")
    @ApiResponse(responseCode = "200", description = "Customer successfully deleted")
     @ApiResponse(responseCode = "404", description = "Customer not found", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentCustomer() throws CustomerNotFoundException {
        customerService.deleteCustomer();
        return ResponseEntity.noContent().build();
    }

}
