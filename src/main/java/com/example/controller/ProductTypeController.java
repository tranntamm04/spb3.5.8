package com.example.controller;

import com.example.dto.ProductTypeDTO;
import com.example.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productType")
@CrossOrigin("*")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping
    public List<ProductTypeDTO> getAll() {
        return productTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ProductTypeDTO getById(@PathVariable int id) {
        return productTypeService.getById(id);
    }

    @PostMapping
    public ProductTypeDTO create(@RequestBody ProductTypeDTO dto) {
        return productTypeService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductTypeDTO update(@PathVariable int id,
                                 @RequestBody ProductTypeDTO dto) {
        return productTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public List<ProductTypeDTO> delete(@PathVariable int id) {
        return productTypeService.delete(id);
    }
}
