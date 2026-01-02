package com.example.service;

import com.example.entity.Evaluates;

import java.util.List;

public interface EvaluatesService {
    void save(Evaluates evaluates);
    List<Evaluates> findAll(int id);
}
