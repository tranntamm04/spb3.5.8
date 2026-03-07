package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem {

    @EmbeddedId
    private CartItemId id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "id_cart")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    private Integer quantity;

    private Float price;
}