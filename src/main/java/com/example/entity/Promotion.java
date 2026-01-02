package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Promotion {
    @Id
    private int idPromotion;
    private String namePromotion;
    private String typePromotion;
    private float promotionalValue;
    private String dateStart;
    private String dateEnd;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;

    public Promotion() {
    }

    public Promotion(int idPromotion, String namePromotion, String typePromotion, float promotionalValue,
            String dateStart, String dateEnd, Set<Product> products) {
        this.idPromotion = idPromotion;
        this.namePromotion = namePromotion;
        this.typePromotion = typePromotion;
        this.promotionalValue = promotionalValue;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.products = products;
    }

}
