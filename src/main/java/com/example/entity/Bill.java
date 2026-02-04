package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private int idBill;

    @Column(name = "date_founded")
    private LocalDateTime dateFounded;

    @Column(name = "received")
    private String received;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "payment_methods")
    private String paymentMethods;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    private Customer customer;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ContractDetail> contractDetails;

    public Bill(LocalDateTime dateFounded, String received, String phone, String address, String paymentMethods,
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

    @PrePersist
    public void prePersist() {
        this.dateFounded = LocalDateTime.now();
    }
}
