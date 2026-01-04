package com.example.service;

import com.example.entity.Promotion;

import java.util.List;

public interface PromotionService {
    Promotion savePromotion(Promotion promotion);
    List<Promotion> getAllPromotions();
    Promotion getPromotionById(int id);
    Promotion updatePromotion(int id, Promotion promotion);
    void deletePromotionById(int id);
}