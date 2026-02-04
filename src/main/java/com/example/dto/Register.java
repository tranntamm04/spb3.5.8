package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class Register {
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
    private String userName;
    @NotNull
    private String password;
    private int status;

    public Register() {
    }

    public Register(@NotNull String idCustomer, @NotNull String surname, @NotNull String name,
            @NotNull String gender, @NotNull String phone,
            @NotNull String email, @NotNull String address, @NotNull String userName, @NotNull String password, int status) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }
}
