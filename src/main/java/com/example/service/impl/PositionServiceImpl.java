package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Position;
import com.example.repository.PositionRepository;
import com.example.service.PositionService;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository positionRepository;

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(int positionId) {
        return positionRepository.findById(positionId).orElse(null);
    }
}
