package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ContractDetail {
    @EmbeddedId
    private BillProductKey id;

    @ManyToOne
    @MapsId("idBill")
    @JoinColumn(name = "id_bill")
    private Bill bill;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    private int quantity;
    private float price;

    public ContractDetail(BillProductKey id, Bill bill, Product product, int quantity, float price) {
        this.id = id;
        this.bill = bill;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}
