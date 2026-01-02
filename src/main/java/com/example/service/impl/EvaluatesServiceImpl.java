package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Evaluates;
import com.example.repository.EvaluatesRepository;
import com.example.service.EvaluatesService;

import java.util.List;

@Service
public class EvaluatesServiceImpl implements EvaluatesService {

    @Autowired
    EvaluatesRepository evaluatesRepository;

    @Override
    public void save(Evaluates evaluates) {
        this.evaluatesRepository.save(evaluates);
    }

    @Override
    public List<Evaluates> findAll(int id) {
        return this.evaluatesRepository.findByIdPro(id);
    }
}
