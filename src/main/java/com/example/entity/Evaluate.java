package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class Evaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvaluate;
    private int numberOfStar;
    private String comment;
    private String dateFounded;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    @JsonBackReference
    private Product product;

    public Evaluate() {
    }

    public Evaluate(int idEvaluate, int numberOfStar, String comment, String dateFounded, Product product) {
        this.idEvaluate = idEvaluate;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.dateFounded = dateFounded;
        this.product = product;
    }

}
