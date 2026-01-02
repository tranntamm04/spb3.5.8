package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
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
    private int numOfStar;
    private String screen;
    private String hdh;
    private String camTr;
    private String camSau;
    private String ram;
    private String rom;
    private String chip;
    private String pin;

    @ManyToOne(targetEntity = Promotion.class)
    @JoinColumn(name = "idPromotion", referencedColumnName = "idPromotion")
    private Promotion promotion;

    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn(name = "idType", referencedColumnName = "idType")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Evaluate> evaluates;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<ContractDetail> contractDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Evaluates> evaluatess;

    public Product() {
    }

    public Product(String productName, String avt, int price, int status, int quantity, int numOfReview, 
    int numOfStar, String screen, String hdh, String camTr, String camSau, String ram, String rom, 
    String chip, String pin, Promotion promotion, ProductType productType) {
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.screen = screen;
        this.hdh = hdh;
        this.camTr = camTr;
        this.camSau = camSau;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.pin = pin;
        this.promotion = promotion;
        this.productType = productType;
    }

    public Product(int idProduct, String productName, String avt, int price, int status, int quantity, 
    int numOfReview, int numOfStar, String screen, String hdh, String camTr, String camSau, String ram, 
    String rom, String chip, String pin, Promotion promotion, ProductType productType, 
    Set<Evaluate> evaluates, Set<ContractDetail> contractDetails) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.screen = screen;
        this.hdh = hdh;
        this.camTr = camTr;
        this.camSau = camSau;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.pin = pin;
        this.promotion = promotion;
        this.productType = productType;
        this.evaluates = evaluates;
        this.contractDetails = contractDetails;
    }

    public Product(int idProduct, String productName, String avt, int price, int status, int quantity, 
    int numOfReview, int numOfStar, String screen, String hdh, String camTr, String camSau, String ram, 
    String rom, String chip, String pin, Promotion promotion, ProductType productType, 
    Set<Evaluate> evaluates, Set<ContractDetail> contractDetails, Set<Evaluates> evaluatess) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.screen = screen;
        this.hdh = hdh;
        this.camTr = camTr;
        this.camSau = camSau;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.pin = pin;
        this.promotion = promotion;
        this.productType = productType;
        this.evaluates = evaluates;
        this.contractDetails = contractDetails;
        this.evaluatess = evaluatess;
    }

}
