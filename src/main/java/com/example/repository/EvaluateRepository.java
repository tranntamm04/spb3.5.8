package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Evaluate;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Integer> {
}
