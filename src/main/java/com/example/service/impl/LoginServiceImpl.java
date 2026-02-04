package com.example.service.impl;

import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.config.JwtTokenUtil;
import com.example.dto.AccountResponse;
import com.example.dto.ChangePasswordForm;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.LoginService;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final AccountService accountService;

    @Override
    public AccountResponse doLogin(String userName, String password) {
        System.out.println();
        Authentication authentication;
        try {
            // Xác thực từ username và password.
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (Exception e) {
            return null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account = this.accountService.findById(userName);
            if (account == null) {
                return null;
            }

        String jwt = jwtTokenUtil.generateJwtToken(userName);
        String role = "";
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                role = "ROLE_ADMIN";
                break;
            } else if ("ROLE_USER".equals(authority.getAuthority())) {
                role = "ROLE_USER";
                break;
            }
        }
        if (role.isEmpty()) {
            return null;
        }
        return new AccountResponse(userName, jwt, role);
    }

    @Override
    public boolean doChangePassword(ChangePasswordForm form) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(),
                    form.getPassword()));
        } catch (Exception e) {
            return false;
        }
        Account account = this.accountService.findById(form.getUsername());
        if (account == null) {
            return false;
        }
        account.setPassword(encode.encode(form.getNewPassword()));
        this.accountService.save(account);
        return true;
    }
}
