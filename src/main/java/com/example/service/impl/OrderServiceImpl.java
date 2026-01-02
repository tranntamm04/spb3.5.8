package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository oderRepository;

    @Override
    public List<Order> findAll() {
        return this.oderRepository.findAll();
    }
}
