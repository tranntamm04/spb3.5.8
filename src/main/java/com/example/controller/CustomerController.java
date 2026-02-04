package com.example.controller;

import com.example.dto.AccountCustomer;
import com.example.dto.Profile;
import com.example.entity.Customer;
import com.example.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/listCustomer")
    public ResponseEntity<Page<Customer>> getAllCustomer(
            @PageableDefault(size = 6) Pageable pageable) {
        return customerService.getAllCustomerResponse(pageable);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomerResponse(id);
    }

    @GetMapping("/viewCustomer/{id}")
    public ResponseEntity<Customer> detailCustomer(@PathVariable String id) {
        return customerService.getCustomerDetailResponse(id);
    }

    @GetMapping("/searchCustomer")
    public ResponseEntity<Page<Customer>> searchCustomer(
            @RequestParam String name,
            @PageableDefault(size = 6) Pageable pageable) {
        return customerService.searchCustomerResponse(name, pageable);
    }

    @GetMapping("/viewUser/{id}")
    public ResponseEntity<AccountCustomer> detailCustomerUser(@PathVariable String id) {
        return customerService.getAccountCustomerResponse(id);
    }

    @GetMapping("/profile")
    public ResponseEntity<Profile> getProfile(Principal principal) {
        return customerService.getProfileResponse(principal);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody Profile dto,
            BindingResult bindingResult,
            Principal principal) {
        return customerService.updateProfileResponse(dto, bindingResult, principal);
    }
}
