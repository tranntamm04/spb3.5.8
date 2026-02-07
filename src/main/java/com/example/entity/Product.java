package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduct;

    private String productName;
    private String avt;
    private int price;
    private int status;
    private int quantity;
    private int numOfReview;

    @Column(columnDefinition = "DECIMAL(2,1)")
    private Double numOfStar;

    @Column(name = "entered_date", updatable = false)
    private LocalDate enteredDate;

    private String description;
    private int sold;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_Promotion", referencedColumnName = "id_Promotion", nullable = true)
    private Promotion promotion;

    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn(name = "idType", referencedColumnName = "idType")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ContractDetail> contractDetails;

    public Product() {
    }

    public Product(String productName, String avt, int price, int status, int quantity,
                   int numOfReview, Double numOfStar, LocalDate enteredDate,
                   String description, int sold,
                   Promotion promotion, ProductType productType) {
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.enteredDate = enteredDate;
        this.description = description;
        this.sold = sold;
        this.promotion = promotion;
        this.productType = productType;
    }

    public Product(int idProduct, String productName, String avt, int price, int status,
                   int quantity, int numOfReview, Double numOfStar,
                   LocalDate enteredDate, String description, int sold,
                   Promotion promotion, ProductType productType,
                   Set<Rating> ratings, Set<ContractDetail> contractDetails) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.enteredDate = enteredDate;
        this.description = description;
        this.sold = sold;
        this.promotion = promotion;
        this.productType = productType;
        this.ratings = ratings;
        this.contractDetails = contractDetails;
    }

    @PrePersist
    public void onCreate() {
        this.enteredDate = LocalDate.now();
    }
}
