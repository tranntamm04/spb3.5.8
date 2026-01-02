package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    @NotBlank
    private String idEmployee;
    private String fullName;
    private String dateOfBirth;
    private String email;
    private String address;
    private String phone;
    private String avtUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Account account;

    @ManyToOne(targetEntity = Position.class)
    @JoinColumn(name = "positionId", referencedColumnName = "positionId")
    private Position position;

    public Employee() {
    }

    public Employee(@NotBlank String idEmployee, String fullName, String dateOfBirth, String email, String address,
            String phone, String avtUrl, Account account, Position position) {
        this.idEmployee = idEmployee;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avtUrl = avtUrl;
        this.account = account;
        this.position = position;
    }

}
