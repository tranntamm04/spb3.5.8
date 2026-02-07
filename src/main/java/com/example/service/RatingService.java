package com.example.service;

import com.example.dto.RatingDTO;
import com.example.entity.Rating;

import java.util.List;

public interface RatingService {
    void createRating(RatingDTO ratingDTO);
    void save(Rating rating);
    List<Rating> findAll(int id);
    List<Rating> findVisible(int id);
    List<Rating> findAllRatings();
    void delete(String customerId, int productId);
    void updateStatus(String customerId, int productId, int status);
}
