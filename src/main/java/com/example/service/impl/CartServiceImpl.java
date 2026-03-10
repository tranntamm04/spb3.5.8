package com.example.service.impl;

import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;

    @Override
    public Cart getCartByUser(String userName) {

        return cartRepository.findByAccountUserName(userName).orElseGet(() -> {
                    Account account = accountRepository.findById(userName)
                            .orElseThrow(() -> new RuntimeException("Account not found"));
                    Cart cart = new Cart();
                    cart.setAccount(account);
                    cart.setCreatedDate(LocalDateTime.now());
                    cart.setStatus(1);

                    return cartRepository.save(cart);
                });
    }

    @Override
    public List<CartItem> getItems(String userName) {
        Cart cart = getCartByUser(userName);
        return cartItemRepository.findByCartId(cart.getId());
    }

    @Override
    @Transactional
    public void addToCart(String userName, Integer idProduct, Integer quantity) {
        Cart cart = getCartByUser(userName);
        Product product = productService.findById(idProduct);
        CartItemId id = new CartItemId(cart.getId(), idProduct);

        CartItem item = cartItemRepository.findById(id).orElse(null);
        if (item == null) {
            item = new CartItem();
            item.setId(id);
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice((float) getFinalPrice(product));
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void updateQuantity(String userName, Integer idProduct, Integer quantity) {
        Cart cart = getCartByUser(userName);
        CartItemId id = new CartItemId(cart.getId(), idProduct);
        CartItem item = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeItem(String userName, Integer idProduct) {
        Cart cart = getCartByUser(userName);
        CartItemId id = new CartItemId(cart.getId(), idProduct);
        cartItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void clearCart(String userName) {
        Cart cart = getCartByUser(userName);
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        cartItemRepository.deleteAll(items);
    }

    private int getFinalPrice(Product product) {
        if (product.getPromotion() == null) {
            return product.getPrice();
        }

        Promotion promo = product.getPromotion();
        double price = product.getPrice();

        if ("PERCENT".equalsIgnoreCase(promo.getTypePromotion())) {
            price = price * (1 - promo.getPromotionalValue() / 100.0);
        }
        else if ("MONEY".equalsIgnoreCase(promo.getTypePromotion())) {
            price = Math.max(price - promo.getPromotionalValue(), 0);
        }

        if (price < 200_000) {
            return (int) (Math.round(price / 1000.0) * 1000);
        }
        return (int) (Math.round(price / 10000.0) * 10000);
    }
}