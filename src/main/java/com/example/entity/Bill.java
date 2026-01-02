package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBill;
    private LocalDate dateFounded;
    private String received;
    private String phone;
    private String address;
    private String paymentMethods;
    private float totalMoney;
    private int status;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "idCustomer", referencedColumnName = "idCustomer")
    // @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<ContractDetail> contractDetails;

    public Bill(LocalDate dateFounded, String received, String phone, String address, String paymentMethods,
            float totalMoney, int status, Customer customer) {
        this.dateFounded = dateFounded;
        this.received = received;
        this.phone = phone;
        this.address = address;
        this.paymentMethods = paymentMethods;
        this.totalMoney = totalMoney;
        this.status = status;
        this.customer = customer;
    }
}
