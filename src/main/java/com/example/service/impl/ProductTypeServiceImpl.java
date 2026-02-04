package com.example.service.impl;

import com.example.dto.ProductTypeDTO;
import com.example.entity.ProductType;
import com.example.repository.ProductTypeRepository;
import com.example.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductTypeDTO> getAll() {
        return productTypeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductTypeDTO getById(int id) {
        ProductType pt = productTypeRepository.findById(id).orElse(null);
        if (pt == null) return null;
        return toDTO(pt);
    }

    @Override
    public ProductTypeDTO create(ProductTypeDTO dto) {
        ProductType pt = toEntity(dto);
        ProductType saved = productTypeRepository.save(pt);
        return toDTO(saved);
    }

    @Override
    public ProductTypeDTO update(int id, ProductTypeDTO dto) {
        ProductType pt = productTypeRepository.findById(id).orElse(null);
        if (pt == null) return null;

        pt.setNameType(dto.getNameType());
        pt.setAvt(dto.getAvt());
        pt.setDescription(dto.getDescription());

        ProductType saved = productTypeRepository.save(pt);
        return toDTO(saved);
    }

    @Override
    public List<ProductTypeDTO> delete(int id) {
        productTypeRepository.deleteById(id);
        return getAll();
    }

    private ProductTypeDTO toDTO(ProductType pt) {
        return new ProductTypeDTO(
                pt.getIdType(),
                pt.getNameType(),
                pt.getAvt(),
                pt.getDescription()
        );
    }

    private ProductType toEntity(ProductTypeDTO dto) {
        ProductType pt = new ProductType();
        pt.setIdType(dto.getIdType());
        pt.setNameType(dto.getNameType());
        pt.setAvt(dto.getAvt());
        pt.setDescription(dto.getDescription());
        return pt;
    }

    @Override
    public ProductType findById(int idType) {
        return this.productTypeRepository.findById(idType).orElse(null);
    }
}

