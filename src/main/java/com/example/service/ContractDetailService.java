package com.example.service;

import com.example.entity.ContractDetail;

import java.util.List;

public interface ContractDetailService {
    void save(ContractDetail contractDetail);
    List<ContractDetail> findAll(int id);
}
