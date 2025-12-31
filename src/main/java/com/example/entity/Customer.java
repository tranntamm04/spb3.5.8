package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private Set<Bill> bills;

    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private Set<Evaluates> evaluates;
}
