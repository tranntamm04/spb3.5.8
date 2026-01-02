package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class EvaluateKey implements Serializable {
    @Column(name = "id_customer")
    private String idCustomer;

    @Column(name = "id_product")
    private int idProduct;

    public EvaluateKey() {
    }

    public EvaluateKey(String idCustomer, int idProduct) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
    }

}
