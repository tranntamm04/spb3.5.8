package com.example.service.impl;

import com.example.dto.AccountCustomer;
import com.example.dto.Profile;
import com.example.dto.Register;
import com.example.entity.*;
import com.example.repository.CustomerRepository;
import com.example.repository.RoleRepository;
import com.example.service.AccountService;
import com.example.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /* ================= CORE LOGIC ================= */

    @Override
    public Page<Customer> getAllCustomer(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> searchCustomer(String name, Pageable pageable) {
        return customerRepository.findByAll(name, pageable);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findByUser(String id) {
        return customerRepository.findByUser(id);
    }

    @Override
    public void register(Register form) {

        if (accountService.findById(form.getUserName()) != null) {
            throw new RuntimeException("USERNAME_EXISTS");
        }

        Account account = new Account();
        account.setUserName(form.getUserName());
        account.setPassword(passwordEncoder.encode(form.getPassword()));

        Customer customer = new Customer();
        customer.setSurname(form.getSurname());
        customer.setName(form.getName());
        customer.setGender(form.getGender());
        customer.setPhone(form.getPhone());
        customer.setEmail(form.getEmail());
        customer.setAddress(form.getAddress());
        customer.setRegisterDate(LocalDate.now());
        customer.setStatus(1);

        customer.setAccount(account);
        account.setCustomer(customer);

        long count = customerRepository.count() + 1;
        customer.setIdCustomer(String.format("KH-%04d", count));

        Role roleUser = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        AccountRole ar = new AccountRole();
        ar.setAccount(account);
        ar.setRole(roleUser);

        AccountRoleKey key = new AccountRoleKey();
        key.setUserName(account.getUserName());
        key.setRoleId(roleUser.getRoleId());
        ar.setId(key);

        account.setAccountRoles(Set.of(ar));
        customerRepository.save(customer);
    }

    /* ================= RESPONSE HANDLER ================= */

    @Override
    public ResponseEntity<Page<Customer>> getAllCustomerResponse(Pageable pageable) {
        Page<Customer> customers = getAllCustomer(pageable);
        return customers.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<?> deleteCustomerResponse(String id) {
        Customer customer = findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> getCustomerDetailResponse(String id) {
        Customer customer = findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(customer);
    }

    @Override
    public ResponseEntity<Page<Customer>> searchCustomerResponse(
            String name, Pageable pageable) {
        Page<Customer> customers = searchCustomer(name, pageable);
        return customers.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<AccountCustomer> getAccountCustomerResponse(String id) {
        Customer customer = findByUser(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccountCustomer dto = new AccountCustomer(
                customer.getIdCustomer(),
                customer.getSurname(),
                customer.getName(),
                customer.getGender(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getRegisterDate(),
                customer.getAccount().getUserName(),
                customer.getAccount().getPassword(),
                customer.getStatus()
        );

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Profile> getProfileResponse(Principal principal) {
        Customer customer = findByUser(principal.getName());
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Profile dto = new Profile();
        dto.setSurname(customer.getSurname());
        dto.setName(customer.getName());
        dto.setGender(customer.getGender());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updateProfileResponse(
            Profile dto, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        Customer customer = findByUser(principal.getName());
        customer.setSurname(dto.getSurname());
        customer.setName(dto.getName());
        customer.setGender(dto.getGender());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        saveCustomer(customer);
        return ResponseEntity.ok().build();
    }
}
