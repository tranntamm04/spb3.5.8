package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private String idCustomer;
    private String surname;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Account account;
    private int status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Bill> bills;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Evaluates> evaluates;

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
            String address, Account account, int status) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.account = account;
        this.status = status;
    }

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
            String address, Account account, int status, Set<Bill> bills) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.account = account;
        this.status = status;
        this.bills = bills;
    }

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
            String address, Account account, int status, Set<Bill> bills, Set<Evaluates> evaluates) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.account = account;
        this.status = status;
        this.bills = bills;
        this.evaluates = evaluates;
    }
}
