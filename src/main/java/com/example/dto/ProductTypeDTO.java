package com.example.dto;

import lombok.*;

@Getter
@Setter
public class ProductTypeDTO {
    private int idType;
    private String nameType;
    private String avt;
    private String description;

    public ProductTypeDTO() {}

    public ProductTypeDTO(int idType, String nameType, String avt, String description) {
        this.idType = idType;
        this.nameType = nameType;
        this.avt = avt;
        this.description = description;
    }
}
