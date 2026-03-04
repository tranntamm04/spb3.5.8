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
    private LocalDateTime dateFounded;
    private String received;
    private String phone;
    private String address;
    private String paymentMethods;
    private float totalMoney;
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
