package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Getter
@Setter
public class AccountEmployee {
    @NotBlank
    private String idEmployee;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private LocalDate dateOfBirth;
    @NotEmpty
    private String email;
    @NotEmpty
    private String address;
    @NotEmpty
    private String phone;
    @NotEmpty
    private LocalDate registerDate;
    private int positionId;
    private String userName;
    private String password;

    public AccountEmployee() {
    }

    public AccountEmployee(@NotBlank String idEmployee, @NotEmpty String fullName, @NotEmpty LocalDate dateOfBirth,
            @NotEmpty String email, @NotEmpty String address, @NotEmpty String phone, @NotEmpty LocalDate registerDate, int positionId,
            String userName, String password) {
        this.idEmployee = idEmployee;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.registerDate = registerDate;
        this.positionId = positionId;
        this.userName = userName;
        this.password = password;
    }
}