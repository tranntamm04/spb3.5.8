package com.example.dto;

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
    private int numOfStar;
    private String screen;
    @NotNull
    private String hdh;
    @NotNull
    private String camTr;
    private String camSau;
    @NotNull
    private String ram;
    @NotNull
    private String rom;
    @NotNull
    private String chip;
    private String pin;
    private int idType;
    private int idPromotion;
    private String nameType;

    public ProductDTO() {
    }

    public ProductDTO(int idProduct, @NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, int numOfStar, String screen, @NotNull String hdh, @NotNull String camTr, String camSau, 
    @NotNull String ram, @NotNull String rom, @NotNull String chip, String pin, int idType, int idPromotion, String nameType) {
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
        this.nameType = nameType;
    }

    public ProductDTO(@NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, int numOfStar, String screen, @NotNull String hdh, @NotNull String camTr, String camSau, 
    @NotNull String ram, @NotNull String rom, @NotNull String chip, String pin, int idType, int idPromotion) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.avt = avt;
        this.screen = screen;
        this.hdh = hdh;
        this.camTr = camTr;
        this.camSau = camSau;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.pin = pin;
        this.idType = idType;
        this.idPromotion = idPromotion;
        this.numOfStar = numOfStar;
        this.numOfReview = numOfReview;
        this.status = status;
    }

    public ProductDTO(int idProduct, @NotNull String productName, String avt, int price, int status, int quantity, 
    int numOfReview, int numOfStar, String screen, @NotNull String hdh, @NotNull String camTr, String camSau, 
    @NotNull String ram, @NotNull String rom, @NotNull String chip, String pin, int idType, int idPromotion) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.avt = avt;
        this.screen = screen;
        this.hdh = hdh;
        this.camTr = camTr;
        this.camSau = camSau;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.pin = pin;
        this.idType = idType;
        this.idPromotion = idPromotion;
        this.numOfStar = numOfStar;
        this.numOfReview = numOfReview;
        this.status = status;
    }
}
