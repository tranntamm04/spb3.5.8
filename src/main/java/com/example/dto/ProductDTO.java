package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ProductDTO {
    private int idProduct;
    @NotNull
    private String productName;
    private String avt;
    private int price;
    private int status;
    private int quantity;
    private int numOfReview;
    private Double numOfStar;
	private LocalDate enteredDate;
	private String description;
	private int sold;
    private int idType;
    private int idPromotion;
    private String nameType;

    public ProductDTO() {
    }

    public ProductDTO(int idProduct, @NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, Double numOfStar, LocalDate enterDate, String description, int sold, int idType, int idPromotion, String nameType) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.avt = avt;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.numOfReview = numOfReview;
        this.numOfStar = numOfStar;
        this.enteredDate = enterDate;
        this.description = description;
        this.sold = sold;
        this.nameType = nameType;
    }

    public ProductDTO(@NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, Double numOfStar, LocalDate enterDate, String description, int sold, int idType, int idPromotion) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.avt = avt;
        this.enteredDate = enterDate;
        this.description = description;
        this.sold = sold;
        this.idType = idType;
        this.idPromotion = idPromotion;
        this.numOfStar = numOfStar;
        this.numOfReview = numOfReview;
        this.status = status;
    }

    public ProductDTO(int idProduct, @NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, Double numOfStar, String screen, LocalDate enterDate, String description, int sold, int idType, int idPromotion) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.avt = avt;
        this.enteredDate = enterDate;
        this.description = description;
        this.sold = sold;
        this.idType = idType;
        this.idPromotion = idPromotion;
        this.numOfStar = numOfStar;
        this.numOfReview = numOfReview;
        this.status = status;
    }
}
