package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Entity
public class Evaluates {
    @EmbeddedId
    private EvaluateKey id;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @MapsId("idCustomer")
    @JoinColumn(name = "id_customer")
    private Customer customer;

    private int numberOfStar;
    private String comment;
    private LocalDate dateFounded;

    public Evaluates() {
    }

    public Evaluates(EvaluateKey id, Product product, Customer customer, int numberOfStar, String comment,
            LocalDate dateFounded) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.dateFounded = dateFounded;
    }

}
