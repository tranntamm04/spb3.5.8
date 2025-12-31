package com.example.service;

import com.example.dto.AccountResponse;
import com.example.dto.ChangePasswordForm;

public interface LoginService {
    AccountResponse doLogin(String userName, String password);
    boolean doChangePassword(ChangePasswordForm form);
}
