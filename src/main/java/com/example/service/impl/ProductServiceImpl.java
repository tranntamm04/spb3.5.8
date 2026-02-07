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
import org.springframework.stereotype.Service;
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
    public List<Product> findByTypeId(int typeId) {
        return productRepository.findByTypeId(typeId);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void createProduct(ProductDTO dto) {

        Promotion promotion = null;
        if (dto.getIdPromotion() != null) {
            promotion = promotionRepository
                    .findById(dto.getIdPromotion())
                    .orElse(null);
        }

        ProductType productType = productTypeRepository
                .findById(dto.getIdType())
                .orElse(null);

        Product product = new Product(
                dto.getProductName(),
                dto.getAvt(),
                dto.getPrice(),
                1,
                dto.getQuantity(),
                0,
                0.0,
                dto.getEnteredDate(),
                dto.getDescription(),
                dto.getSold(),
                promotion,
                productType
        );

        saveProduct(product);
    }


    @Override
    public void updateProduct(ProductDTO dto, int id) {
        Product product = findById(id);
        if (product == null) return;

        Promotion promotion = null;
        if (dto.getIdPromotion() != null) {
            promotion = promotionRepository
                    .findById(dto.getIdPromotion())
                    .orElse(null);
        }

        ProductType productType = productTypeRepository
                .findById(dto.getIdType())
                .orElse(null);

        mapDtoToProduct(dto, product, promotion, productType);
        saveProduct(product);
    }

    @Override
    public Page<Product> getSearchItem(String itemSearch, Pageable pageable) {
        return productRepository.searchItem(itemSearch, pageable);
    }

    @Override
    public boolean importStock(int id, int quantity) {
        Product product = findById(id);
        if (product == null) return false;

        product.setQuantity(product.getQuantity() + quantity);
        saveProduct(product);
        return true;
    }

    private void mapDtoToProduct(
            ProductDTO dto,
            Product product,
            Promotion promotion,
            ProductType productType) {

        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setAvt(dto.getAvt());
        product.setEnteredDate(dto.getEnteredDate());
        product.setDescription(dto.getDescription());
        product.setSold(dto.getSold());
        product.setPromotion(promotion);
        product.setProductType(productType);
    }

    @Override
    public Product findByIdWithPromotion(int id) {
        return productRepository.findByIdWithPromotion(id);
    }
}
