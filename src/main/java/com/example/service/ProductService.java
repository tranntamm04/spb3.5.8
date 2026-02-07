package com.example.service;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> getAllProduct(Pageable pageable);
    Product findById(int id);
    boolean existsById(int id);
    void deleteProduct(int id);
    void saveProduct(Product product);
    void createProduct(ProductDTO dto);
    void updateProduct(ProductDTO dto, int id);
    Page<Product> getSearchItem(String itemSearch, Pageable pageable);
    boolean importStock(int id, int quantity);
    Product findByIdWithPromotion(int id);
    List<Product> findByTypeId(int typeId);
}
