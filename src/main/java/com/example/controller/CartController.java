package com.example.controller;

import com.example.entity.CartItem;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userName}")
    public List<CartItem> getCartItems(@PathVariable String userName) {

        return cartService.getItems(userName);
    }

    @PostMapping("/add")
    public void addToCart(
            @RequestParam String userName,
            @RequestParam Integer idProduct,
            @RequestParam Integer quantity) {

        cartService.addToCart(userName, idProduct, quantity);
    }

    @PutMapping("/update")
    public void updateQuantity(
            @RequestParam String userName,
            @RequestParam Integer idProduct,
            @RequestParam Integer quantity) {

        cartService.updateQuantity(userName, idProduct, quantity);
    }

    @DeleteMapping("/remove")
    public void removeItem(
            @RequestParam String userName,
            @RequestParam Integer idProduct) {

        cartService.removeItem(userName, idProduct);
    }

    @DeleteMapping("/clear/{userName}")
    public void clearCart(@PathVariable String userName) {
        cartService.clearCart(userName);
    }
}