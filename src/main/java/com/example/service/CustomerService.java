package com.example.service;

import com.example.dto.AccountCustomer;
import com.example.dto.Profile;
import com.example.dto.Register;
import com.example.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.security.Principal;

public interface CustomerService {

    Page<Customer> getAllCustomer(Pageable pageable);
    Customer findById(String id);
    void deleteCustomer(String id);
    Page<Customer> searchCustomer(String name, Pageable pageable);
    void saveCustomer(Customer customer);
    Customer findByUser(String id);
    void register(Register form);

    ResponseEntity<Page<Customer>> getAllCustomerResponse(Pageable pageable);
    ResponseEntity<?> deleteCustomerResponse(String id);
    ResponseEntity<Customer> getCustomerDetailResponse(String id);
    ResponseEntity<Page<Customer>> searchCustomerResponse(String name, Pageable pageable);
    ResponseEntity<AccountCustomer> getAccountCustomerResponse(String id);
    ResponseEntity<Profile> getProfileResponse(Principal principal);
    ResponseEntity<?> updateProfileResponse(
            Profile dto, BindingResult bindingResult, Principal principal);
}
