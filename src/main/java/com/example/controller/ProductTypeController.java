package com.example.controller;

import com.example.dto.ProductTypeDTO;
import com.example.entity.ProductType;
import com.example.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productType")
@CrossOrigin("*")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    // Lấy tất cả danh mục
    @GetMapping
    public List<ProductTypeDTO> getAll() {
        return productTypeService.findAll().stream()
                .map(pt -> new ProductTypeDTO(
                        pt.getIdType(),
                        pt.getNameType(),
                        pt.getAvt(),
                        pt.getDescription()))
                .collect(Collectors.toList());
    }

    // Lấy 1 danh mục theo id
    @GetMapping("/{id}")
    public ProductTypeDTO getById(@PathVariable int id) {
        ProductType pt = productTypeService.findById(id);
        if (pt == null)
            return null;
        return new ProductTypeDTO(
                pt.getIdType(),
                pt.getNameType(),
                pt.getAvt(),
                pt.getDescription());
    }

    // Tạo mới danh mục (POST)
    @PostMapping
    public ProductTypeDTO create(@RequestBody ProductTypeDTO dto) {
        // map DTO -> Entity
        ProductType pt = new ProductType();
        pt.setIdType(dto.getIdType()); // ID phải gửi từ client
        pt.setNameType(dto.getNameType());
        pt.setAvt(dto.getAvt());
        pt.setDescription(dto.getDescription());

        ProductType saved = productTypeService.save(pt);

        return new ProductTypeDTO(
                saved.getIdType(),
                saved.getNameType(),
                saved.getAvt(),
                saved.getDescription());
    }

    // Cập nhật danh mục (PUT)
    @PutMapping("/{id}")
    public ProductTypeDTO update(@PathVariable int id, @RequestBody ProductTypeDTO dto) {
        ProductType pt = productTypeService.findById(id);
        if (pt == null)
            return null;

        pt.setNameType(dto.getNameType());
        pt.setAvt(dto.getAvt());
        pt.setDescription(dto.getDescription());

        ProductType saved = productTypeService.save(pt);

        return new ProductTypeDTO(
                saved.getIdType(),
                saved.getNameType(),
                saved.getAvt(),
                saved.getDescription());
    }

    @DeleteMapping("/{id}")
    public List<ProductTypeDTO> delete(@PathVariable int id) {
        productTypeService.deleteById(id);
        return productTypeService.findAll().stream()
                .map(pt -> new ProductTypeDTO(pt.getIdType(), pt.getNameType(), pt.getAvt(), pt.getDescription()))
                .collect(Collectors.toList());
    }

}
