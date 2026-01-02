package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class AccountEmployee {
    @NotBlank
    private String idEmployee;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String dateOfBirth;
    @NotEmpty
    private String email;
    @NotEmpty
    private String address;
    @NotEmpty
    private String phone;
    private String avtUrl;
    private int positionId;
    private String userName;
    private String password;

    public AccountEmployee() {
    }

    public AccountEmployee(@NotBlank String idEmployee, @NotEmpty String fullName, @NotEmpty String dateOfBirth,
            @NotEmpty String email, @NotEmpty String address, @NotEmpty String phone, String avtUrl, int positionId,
            String userName, String password) {
        this.idEmployee = idEmployee;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avtUrl = avtUrl;
        this.positionId = positionId;
        this.userName = userName;
        this.password = password;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
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

}
