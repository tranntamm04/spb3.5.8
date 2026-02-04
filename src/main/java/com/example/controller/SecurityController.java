package com.example.controller;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dto.AccountResponse;
import com.example.dto.ChangePasswordForm;
import com.example.dto.Login;
import com.example.service.LoginService;
import com.example.dto.Register;
import com.example.service.CustomerService;

@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RestController
public class SecurityController {

    private final LoginService loginService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<AccountResponse> doLogin(@RequestBody Login form) {
        AccountResponse account = loginService.doLogin(form.getUserName(), form.getPassword());
        return account == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(account);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Register form) {
        try {
            customerService.register(form);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            if ("USERNAME_EXISTS".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            throw e;
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> doChangePassword(@RequestBody ChangePasswordForm form) {
        if (loginService.doChangePassword(form)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/random")
    public ResponseEntity<AccountResponse> randomStuff() {
        return new ResponseEntity<>(new AccountResponse("Check jwt thành công"),
                HttpStatus.OK);
    }
}
