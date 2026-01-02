package com.example.jwt;

import com.example.entity.Account;
import com.example.entity.Employee;

import java.util.List;

public class JwtResponseEmployee {
    private String token;
    private String type = "Bearer";
    private Account account;
    private Employee employee;
    private List<String> roles;

    public JwtResponseEmployee(String token, Account account, Employee employee, List<String> roles) {
        this.token = token;
        this.account = account;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
