package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.ProductType;
import com.example.repository.ProductTypeRepository;
import com.example.service.ProductTypeService;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductType> findAll() {
        return this.productTypeRepository.findAll();
    }

    @Override
    public ProductType findById(int idType) {
        return this.productTypeRepository.findById(idType).orElse(null);
    }

    @Override
    public ProductType save(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    @Override
    public void deleteById(int idType) {
        this.productTypeRepository.deleteById(idType);
    }

}
