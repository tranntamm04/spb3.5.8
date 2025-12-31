package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.AccountRole;
import com.example.repository.AccountRoleRepository;
import com.example.service.AccountRoleService;

@Service
public class AccountRoleServiceImpl implements AccountRoleService {
    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Override
    public void save(AccountRole accountRole) {
        accountRoleRepository.save(accountRole);
    }
}
