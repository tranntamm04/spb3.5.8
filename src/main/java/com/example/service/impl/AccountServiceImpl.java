package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import lombok.*;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findById(String userName) {
        return this.accountRepository.findById(userName).orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return this.accountRepository.findAll();
    }
}
