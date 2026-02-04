package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCustomer {
    private String idCustomer;
    @NotNull
    private String surname;
    @NotNull
    private String name;
    @NotNull
    private String gender;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String address;
    @NotNull
    private LocalDate registerDate;
    @NotNull
    private String userName;
    private String password;
    private int status;
}
 