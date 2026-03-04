package com.example.service;

import com.example.entity.*;
import com.example.repository.CustomerRepository;
import com.example.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public void processOAuth2User(String email, String name, String provider) {

        if (accountService.findById(email) != null) {
            return;
        }

        Account account = new Account();
        account.setUserName(email);
        account.setPassword(provider);
        accountService.save(account);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setRegisterDate(LocalDate.now());
        customer.setStatus(1);

        long count = customerRepository.count() + 1;
        customer.setIdCustomer(String.format("KH-%04d", count));

        customer.setAccount(account);
        account.setCustomer(customer);

        Role roleUser = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        AccountRole ar = new AccountRole();
        ar.setAccount(account);
        ar.setRole(roleUser);
        ar.setId(new AccountRoleKey(account.getUserName(), roleUser.getRoleId()));

        account.setAccountRoles(Set.of(ar));

        customerRepository.save(customer);
    }
}
