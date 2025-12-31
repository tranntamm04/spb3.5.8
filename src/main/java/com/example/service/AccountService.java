package com.example.service;

import com.example.entity.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);

    Account findById(String userName);

    List<Account> findAll();
}
