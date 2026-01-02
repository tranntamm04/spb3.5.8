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
public class BillProductKey implements Serializable {

    @Column(name = "id_bill")
    private int idBill;

    @Column(name = "id_product")
    private int idProduct;
}
