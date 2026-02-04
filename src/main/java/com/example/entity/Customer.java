package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "id_customer")
    private String idCustomer;

    private String surname;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private LocalDate registerDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Account account;

    private int status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Bill> bills;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Rating> ratings;

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
                    String address, LocalDate registerDate, Account account, int status) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerDate = registerDate;
        this.account = account;
        this.status = status;
    }

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
                    String address, LocalDate registerDate, Account account, int status, Set<Bill> bills) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerDate = registerDate;
        this.account = account;
        this.status = status;
        this.bills = bills;
    }

    public Customer(String idCustomer, String surname, String name, String gender, String phone, String email,
                    String address, LocalDate registerDate, Account account, int status,
                    Set<Bill> bills, Set<Rating> ratings) {
        this.idCustomer = idCustomer;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerDate = registerDate;
        this.account = account;
        this.status = status;
        this.bills = bills;
        this.ratings = ratings;
    }
}
