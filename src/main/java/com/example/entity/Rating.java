package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @EmbeddedId
    private RatingKey id;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @MapsId("idCustomer")
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column(nullable = false)
    private int numberOfStar;

    @Column(columnDefinition = "TEXT")
    private String comment;
    @Column
    private int status;
    private LocalDateTime dateFounded;
    
    public Rating(RatingKey key, Product product, Customer customer, int numberOfStar, String comment, LocalDateTime dateFounded) {
        this.id = key;
        this.product = product;
        this.customer = customer;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.dateFounded = dateFounded;
    }

    @PrePersist
    public void onCreate() {
        this.dateFounded = LocalDateTime.now();
        this.status = 1;
    }
}
