package com.example.jwt;

import com.example.entity.Account;
import com.example.entity.Customer;

import java.util.List;

//@Data
//@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Account account;
    private Customer customer;
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(String token, Account account, List<String> roles) {
        this.token = token;
        this.account = account;
        this.roles = roles;
    }

    public JwtResponse(String token, Account account, Customer customer, List<String> roles) {
        this.token = token;
        this.account = account;
        this.customer = customer;
        this.roles = roles;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
