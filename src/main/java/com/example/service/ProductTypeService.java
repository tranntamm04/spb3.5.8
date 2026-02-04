package com.example.service;

import com.example.dto.ProductTypeDTO;
import com.example.entity.ProductType;

import java.util.List;

public interface ProductTypeService {
    ProductType findById(int idType);
    List<ProductTypeDTO> getAll();
    ProductTypeDTO getById(int id);
    ProductTypeDTO create(ProductTypeDTO dto);
    ProductTypeDTO update(int id, ProductTypeDTO dto);
    List<ProductTypeDTO> delete(int id);
}
