package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDTO {
    private int idBill;
    private LocalDateTime dateFounded;
    private String received;
    private String phone;
    private String address;
    private String paymentMethods;
    private float totalMoney;
    private int status;
    private String idCustomer;
    private List<ProductDTO> object;

    public BillDTO() {
    }

    public BillDTO(int idBill, LocalDateTime dateFounded, String received,
            String phone, String address, String paymentMethods, float totalMoney, int status, String idCustomer) {
        this.idBill = idBill;
        this.dateFounded = dateFounded;
        this.received = received;
        this.phone = phone;
        this.address = address;
        this.paymentMethods = paymentMethods;
        this.totalMoney = totalMoney;
        this.status = status;
        this.idCustomer = idCustomer;
    }

    public BillDTO(LocalDateTime dateFounded, String received, String phone, String address, String paymentMethods,
            float totalMoney, int status, String idCustomer) {
        this.dateFounded = dateFounded;
        this.received = received;
        this.phone = phone;
        this.address = address;
        this.paymentMethods = paymentMethods;
        this.totalMoney = totalMoney;
        this.status = status;
        this.idCustomer = idCustomer;
    }
}
