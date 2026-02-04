package com.example.service.impl;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.entity.ProductType;
import com.example.entity.Promotion;
import com.example.repository.ProductRepository;
import com.example.repository.ProductTypeRepository;
import com.example.repository.PromotionRepository;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final ProductTypeRepository productTypeRepository;

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCustomer(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(ProductDTO productDTO, int id) {
        Product product = findById(id);
        if (product == null) return;

        Promotion promotion =
                promotionRepository.findById(productDTO.getIdPromotion()).orElse(null);
        ProductType productType =
                productTypeRepository.findById(productDTO.getIdType()).orElse(null);

        product.setPromotion(promotion);
        product.setProductType(productType);
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setAvt(productDTO.getAvt());
        product.setEnteredDate(productDTO.getEnteredDate());
        product.setDescription(productDTO.getDescription());
        product.setSold(productDTO.getSold());

        productRepository.save(product);
    }

    @Override
    public Page<Product> getSearchTag(String t, Pageable pageable) {
        return productRepository.searchTag(t, pageable);
    }

    @Override
    public Page<Product> getSearchItem(String itemSearch, Pageable pageable) {
        return productRepository.searchItem(itemSearch, pageable);
    }

    @Override
    public ResponseEntity<Page<Product>> getAllProductResponse(Pageable pageable) {
        Page<Product> products = getAllProduct(pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Void> deleteProductResponse(int id) {
        Product product = findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<FieldError>> createProductResponse(
            ProductDTO productDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        Promotion promotion =
                promotionRepository.findById(productDTO.getIdPromotion()).orElse(null);
        ProductType productType =
                productTypeRepository.findById(productDTO.getIdType()).orElse(null);

        Product product = new Product(
                productDTO.getProductName(),
                productDTO.getAvt(),
                productDTO.getPrice(),
                1,
                productDTO.getQuantity(),
                0,
                0.0,
                productDTO.getEnteredDate(),
                productDTO.getDescription(),
                productDTO.getSold(),
                promotion,
                productType
        );

        saveProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductDTO> getDetailProductResponse(int id) {
        Product product = findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductDTO dto = new ProductDTO(
                product.getIdProduct(),
                product.getProductName(),
                product.getAvt(),
                product.getPrice(),
                product.getStatus(),
                product.getQuantity(),
                product.getNumOfReview(),
                product.getNumOfStar(),
                product.getEnteredDate(),
                product.getDescription(),
                product.getSold(),
                product.getProductType().getIdType(),
                product.getPromotion().getIdPromotion(),
                product.getProductType().getNameType()
        );

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Product> getProductPromotionResponse(int id) {
        Product product = findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<?> updateProductResponse(
            ProductDTO productDTO, BindingResult bindingResult, int id) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        update(productDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Product>> searchTagResponse(String t, Pageable pageable) {
        Page<Product> products = getSearchTag(t, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Page<Product>> searchItemResponse(
            String itemSearch, Pageable pageable) {
        Page<Product> products = getSearchItem(itemSearch, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<?> importStockResponse(int id, int quantity) {
        Product product = findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setQuantity(product.getQuantity() + quantity);
        saveProduct(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
