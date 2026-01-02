package com.example.service;

import com.example.entity.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> findAll();
    Promotion findById(int idPromotion);
}
