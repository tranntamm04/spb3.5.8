package com.example.repository;

import com.example.entity.CartItem;
import com.example.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    List<CartItem> findByCartId(Integer cartId);

}