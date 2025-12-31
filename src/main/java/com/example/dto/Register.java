package com.example.dto;

import jakarta.validation.constraints.NotNull;

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
    private String password;
    private int status;

    public Register() {
    }

    public Register(@NotNull String idCustomer, @NotNull String surname, @NotNull String name,
            @NotNull String gender, @NotNull String phone,
            @NotNull String email, @NotNull String address, @NotNull String userName, String password, int status) {
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

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
