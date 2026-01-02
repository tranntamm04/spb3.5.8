package com.example.service;

import com.example.entity.Position;

import java.util.List;

public interface PositionService {
    List<Position> findAll();
    Position findById(int positionId);
}
