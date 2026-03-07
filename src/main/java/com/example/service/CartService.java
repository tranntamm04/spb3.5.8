package com.example.service;

import com.example.entity.Cart;
import com.example.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart getCartByUser(String userName);

    List<CartItem> getItems(String userName);

    void addToCart(String userName, Integer idProduct, Integer quantity);

    void updateQuantity(String userName, Integer idProduct, Integer quantity);

    void removeItem(String userName, Integer idProduct);

    void clearCart(String userName);
}