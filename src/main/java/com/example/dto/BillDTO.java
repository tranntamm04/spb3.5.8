package com.example.dto;

import java.time.LocalDate;
import java.util.List;

public class BillDTO {
    private int idBill;
    private LocalDate dateFounded;
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

    public BillDTO(int idBill, LocalDate dateFounded, String received,
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

    public BillDTO(LocalDate dateFounded, String received, String phone, String address, String paymentMethods,
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

    public List<ProductDTO> getObject() {
        return object;
    }

    public void setObject(List<ProductDTO> object) {
        this.object = object;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public LocalDate getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(LocalDate dateFounded) {
        this.dateFounded = dateFounded;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
