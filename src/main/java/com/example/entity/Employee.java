package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private String idEmployee;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
    private String phone;
    private LocalDate registerDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Account account;

    @ManyToOne(targetEntity = Position.class)
    @JoinColumn(name = "positionId", referencedColumnName = "positionId")
    private Position position;

    public Employee() {
    }

    public Employee(@NotBlank String idEmployee, String fullName, LocalDate dateOfBirth, String email, String address,
            String phone, LocalDate registerDate, Account account, Position position) {
        this.idEmployee = idEmployee;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.registerDate = registerDate;
        this.account = account;
        this.position = position;
    }

}
