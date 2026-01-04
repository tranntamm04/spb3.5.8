package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.dto.AccountCustomer;
import com.example.entity.*;
import com.example.service.AccountRoleService;
import com.example.service.AccountService;
import com.example.service.CustomerService;
import com.example.service.RoleService;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/api/customer")
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    RoleService roleService;

    @Autowired
    AccountRoleService accountRoleService;

    @Autowired
    AccountService accountService;

    @Autowired
    MailSender mailSender;

    @RequestMapping(value = "/listCustomer", method = RequestMethod.GET)
    public ResponseEntity<Page<Customer>> getAllCustomer(@PageableDefault(size = 6) Pageable pageable) {
        Page<Customer> customerList = customerService.getAllCustomer(pageable);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") String id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(id);
        return new ResponseEntity<Customer>(HttpStatus.OK);
    }

    @GetMapping(value = "/viewCustomer/{id}")
    public ResponseEntity<Customer> detailCustomer(@PathVariable String id) {
        Customer customer = this.customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchCustomer", method = RequestMethod.GET)
    public ResponseEntity<Page<Customer>> searchCustomer(@RequestParam String name,
            @PageableDefault(size = 6) Pageable pageable) {
        Page<Customer> customerPage = customerService.searchCustomer(name, pageable);
        if (customerPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<List<FieldError>> createCustomer(@RequestBody @Valid AccountCustomer accountCustomer,
            BindingResult bindingResult) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode(accountCustomer.getPassword());
        Account account = new Account(accountCustomer.getUserName(), pass);
        accountService.save(account);
        AccountRoleKey accountRoleKey = new AccountRoleKey(account.getUserName(), 2);
        Role role = roleService.findById(2);
        AccountRole accountRole = new AccountRole(accountRoleKey, account, role);
        accountRoleService.save(accountRole);
        int a = (int) (Math.random() * 1000);
        String maKH = "KH-" + a;
        Customer customer = new Customer(maKH, accountCustomer.getSurname(), accountCustomer.getName(),
                accountCustomer.getGender(), accountCustomer.getPhone(),
                accountCustomer.getEmail(), accountCustomer.getAddress(),
                account, 1);
        customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // Mail
    public void sendEmail(String from, String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
    }

    @GetMapping(value = "/viewUser/{id}")
    public ResponseEntity<AccountCustomer> detailCustomerUser(@PathVariable String id) {
        Customer customer = this.customerService.findByUser(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AccountCustomer accountCustomer = new AccountCustomer(customer.getIdCustomer(), customer.getSurname(),
                customer.getName(), customer.getGender(),
                customer.getPhone(), customer.getEmail(), customer.getAddress(), customer.getAccount().getUserName(),
                customer.getAccount().getPassword(), customer.getStatus());
        return new ResponseEntity<>(accountCustomer, HttpStatus.OK);
    }
}
