package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class RatingKey implements Serializable {

    @Column(name = "id_customer")
    private String idCustomer;

    @Column(name = "id_product")
    private int idProduct;
}
