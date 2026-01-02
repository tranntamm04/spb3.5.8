package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
