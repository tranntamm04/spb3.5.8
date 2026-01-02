package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.dto.ProductDTO;
import com.example.entity.Product;

public interface ProductService {
    Page<Product> getAllProduct(Pageable pageable);
    Product findById(int id);
    void deleteCustomer(int id);
    void saveProduct(Product product);
    void update(ProductDTO productDTO, int id);
    Page<Product> getSearchTag(String t, Pageable pageable);
    Page<Product> getSearchItem(String itemSearch, Pageable pageable);

}
