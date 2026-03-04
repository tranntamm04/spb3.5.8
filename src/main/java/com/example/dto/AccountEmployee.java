package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEmployee {
    private String idEmployee;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
    private String phone;
    private LocalDate registerDate;
    private int positionId;
    private String userName;
    private String password;
}