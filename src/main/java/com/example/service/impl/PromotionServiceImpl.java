package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Promotion;
import com.example.repository.PromotionRepository;
import com.example.service.PromotionService;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getPromotionById(int id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi id=" + id));
    }

    @Override
    public Promotion updatePromotion(int id, Promotion promotionDetails) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi id=" + id));
        // Cập nhật các trường
        if (promotionDetails.getNamePromotion() != null) {
            promotion.setNamePromotion(promotionDetails.getNamePromotion());
        }
        if (promotionDetails.getTypePromotion() != null) {
            promotion.setTypePromotion(promotionDetails.getTypePromotion());
        }
        promotion.setPromotionalValue(promotionDetails.getPromotionalValue());
        promotion.setDateStart(promotionDetails.getDateStart());
        promotion.setDateEnd(promotionDetails.getDateEnd());
        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotionById(int id) {
        promotionRepository.deleteById(id);
    }
}
