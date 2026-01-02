package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor 
public class Account {
    @Id
    @NotBlank
    private String userName;
    @Column(length = 255)
    private String password;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer customer;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonBackReference
    private Employee employee;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<AccountRole> accountRoles;

    public Account(@NotBlank String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(@NotBlank String userName, String password, Customer customer, Employee employee,
            Set<AccountRole> accountRoles) {
        this.userName = userName;
        this.password = password;
        this.customer = customer;
        this.employee = employee;
        this.accountRoles = accountRoles;
    }

}