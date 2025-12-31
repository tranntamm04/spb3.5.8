package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.entity.AccountRole;
import com.example.repository.AccountRepository;
import com.example.repository.AccountRoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByUserName(userName);

        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        List<AccountRole> roles = this.accRoleRepository.findAllByAccount(account);
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roles != null) {
            for (AccountRole role1 : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role1.getRole().getRoleName());
                grantList.add(authority);
            }
        }

        return new User(account.getUserName(), account.getPassword(), grantList);
    }
}
