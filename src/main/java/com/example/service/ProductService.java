package com.example.service;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public interface ProductService {

    Page<Product> getAllProduct(Pageable pageable);
    Product findById(int id);
    void deleteCustomer(int id);
    void saveProduct(Product product);
    void update(ProductDTO productDTO, int id);
    Page<Product> getSearchTag(String t, Pageable pageable);
    Page<Product> getSearchItem(String itemSearch, Pageable pageable);

    ResponseEntity<Page<Product>> getAllProductResponse(Pageable pageable);
    ResponseEntity<Void> deleteProductResponse(int id);
    ResponseEntity<List<FieldError>> createProductResponse(
            ProductDTO dto, BindingResult bindingResult);
    ResponseEntity<ProductDTO> getDetailProductResponse(int id);
    ResponseEntity<Product> getProductPromotionResponse(int id);
    ResponseEntity<?> updateProductResponse(
            ProductDTO dto, BindingResult bindingResult, int id);
    ResponseEntity<Page<Product>> searchTagResponse(String t, Pageable pageable);
    ResponseEntity<Page<Product>> searchItemResponse(String itemSearch, Pageable pageable);
    ResponseEntity<?> importStockResponse(int id, int quantity);
}
