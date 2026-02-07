package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer idPromotion;
    private String nameType;
}

