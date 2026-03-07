package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Integer id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Integer status;

    @OneToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName")
    private Account account;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;
}