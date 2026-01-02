package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}
