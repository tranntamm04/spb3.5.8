package com.example.dto;

import java.time.LocalDate;

public class EvaluateDTO {
    private int idEvaluate;
    private int numberOfStar;
    private String comment;
    private LocalDate dateFounded;
    private int idProduct;
    private String idCustomer;

    public EvaluateDTO() {
    }

    public EvaluateDTO(int idEvaluate, int numberOfStar, String comment, LocalDate dateFounded, int idProduct) {
        this.idEvaluate = idEvaluate;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.dateFounded = dateFounded;
        this.idProduct = idProduct;
    }

    public EvaluateDTO(int idEvaluate, int numberOfStar, String comment, LocalDate dateFounded, int idProduct,
            String idCustomer) {
        this.idEvaluate = idEvaluate;
        this.numberOfStar = numberOfStar;
        this.comment = comment;
        this.dateFounded = dateFounded;
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdEvaluate() {
        return idEvaluate;
    }

    public void setIdEvaluate(int idEvaluate) {
        this.idEvaluate = idEvaluate;
    }

    public int getNumberOfStar() {
        return numberOfStar;
    }

    public void setNumberOfStar(int numberOfStar) {
        this.numberOfStar = numberOfStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(LocalDate dateFounded) {
        this.dateFounded = dateFounded;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
