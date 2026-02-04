package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private int numberOfStar;
    private String comment;
    private LocalDate dateFounded;
    private int idProduct;
    private String idCustomer;
    private int status;
}