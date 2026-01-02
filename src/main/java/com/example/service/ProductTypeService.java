package com.example.service;

import com.example.entity.ProductType;

import java.util.List;

public interface ProductTypeService {
    List<ProductType> findAll();
    ProductType findById(int idType);
    ProductType save(ProductType productType);
    void deleteById(int idType);
}
