package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.dto.AccountResponse;
import com.example.dto.ChangePasswordForm;
import com.example.dto.Login;
import com.example.service.LoginService;
import com.example.dto.Register;
import com.example.entity.Account;
import com.example.entity.AccountRole;
import com.example.entity.AccountRoleKey;
import com.example.entity.Customer;
import com.example.service.AccountService;
import com.example.service.CustomerService;
import com.example.repository.RoleRepository;
import com.example.entity.Role;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin("http://localhost:4200")
@RestController
public class SecurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping(value = "/login")
    public ResponseEntity<AccountResponse> doLogin(@RequestBody Login form) {
        AccountResponse account = this.loginService.doLogin(form.getUserName(),
                form.getPassword());
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register form) {

        if (accountService.findById(form.getUserName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("{\"message\":\"username already exists\"}");
        }

        Account account = new Account();
        account.setUserName(form.getUserName());
        account.setPassword(passwordEncoder.encode(form.getPassword()));

        Customer customer = new Customer();
        customer.setIdCustomer(form.getIdCustomer());
        customer.setSurname(form.getSurname());
        customer.setName(form.getName());
        customer.setGender(form.getGender());
        customer.setPhone(form.getPhone());
        customer.setEmail(form.getEmail());
        customer.setAddress(form.getAddress());
        customer.setStatus(1);

        account.setCustomer(customer);
        customer.setAccount(account);

        Role roleUser = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        AccountRole ar = new AccountRole();
        ar.setAccount(account);
        ar.setRole(roleUser);

        AccountRoleKey key = new AccountRoleKey();
        key.setUserName(account.getUserName());
        key.setRoleId(roleUser.getRoleId());
        ar.setId(key);

        account.setAccountRoles(new HashSet<>(Set.of(ar)));

        customerService.saveCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"message\":\"register success\"}");
    }

    
    @PostMapping(value = "/change-password")
    public ResponseEntity<String> doChangePassword(@RequestBody ChangePasswordForm form) {
        if (this.loginService.doChangePassword(form)) {
            return new ResponseEntity<>("{\"message\": \"success\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\": \"fail\"}", HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<AccountResponse> randomStuff() {
        return new ResponseEntity<>(new AccountResponse("Kiểm tra jwt thành công"),
                HttpStatus.OK);
    }
}
