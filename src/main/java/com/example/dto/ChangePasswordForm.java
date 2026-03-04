package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordForm {
    private String username;
    private String password;
    private String newPassword;
}
