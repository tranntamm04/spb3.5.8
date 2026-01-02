package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int maSp;
    private String tenSp;
    private String img;
    private int gia;
    private int soLuong;
    private String maKH;

    public Order() {
    }

    public Order(int maSp, String tenSp, String img, int gia, int soLuong, String maKH) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.img = img;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maKH = maKH;
    }

    public Order(String tenSp, String img, int gia, int soLuong, String maKH) {
        this.tenSp = tenSp;
        this.img = img;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maKH = maKH;
    }

}
