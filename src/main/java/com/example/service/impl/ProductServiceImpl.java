package com.example.service.impl;

import com.example.dto.ProductDTO;
import com.example.entity.InventoryHistory;
import com.example.entity.Product;
import com.example.entity.ProductType;
import com.example.entity.Promotion;
import com.example.repository.InventoryHistoryRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ProductTypeRepository;
import com.example.repository.PromotionRepository;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final ProductTypeRepository productTypeRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;

    private void removeExpiredPromotion(Product product){

        if(product != null && product.getPromotion() != null){
            if(product.getPromotion().getDateEnd().isBefore(java.time.LocalDate.now())){
                product.setPromotion(null);
            }

        }
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        page.forEach(this::removeExpiredPromotion);

        return page;
    }

    @Override
    public List<Product> findByTypeId(int typeId) {
        List<Product> list = productRepository.findByTypeId(typeId);
        list.forEach(this::removeExpiredPromotion);

        return list;
    }

    @Override
    public Product findById(int id) {
        Product p = productRepository.findById(id).orElse(null);
        removeExpiredPromotion(p);

        return p;
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
    public Page<Product> getAccessories(Pageable pageable) {
        return productRepository.findAccessories(pageable);
    }

    @Override
    public Page<Product> getSearchItem(String itemSearch, Pageable pageable) {
        Page<Product> page = productRepository.searchItem(itemSearch, pageable);
        page.forEach(this::removeExpiredPromotion);

        return page;
    }

    @Transactional
    @Override
    public boolean importStock(int id, int quantity, String note) {

        if (quantity == 0) return false;

        Product product = findById(id);
        if (product == null) return false;

        int newQuantity = product.getQuantity() + quantity;
        if (newQuantity < 0) return false;

        product.setQuantity(newQuantity);
        productRepository.save(product);

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        InventoryHistory history = new InventoryHistory();
        history.setProduct(product);
        history.setQuantityChanged(quantity);
        history.setNote(note);
        history.setCreatedBy(username);

        inventoryHistoryRepository.save(history);

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
        Product product = productRepository.findByIdWithPromotion(id);
        removeExpiredPromotion(product);

        return product;
    }

    @Override
    public List<String> suggestKeyword(String keyword) {
        return productRepository.suggestKeyword(keyword);
    }

    @Override
    public List<Product> suggestProduct(String keyword) {
        List<Product> list = productRepository.suggestProduct(keyword, PageRequest.of(0,4));
        list.forEach(this::removeExpiredPromotion);

        return list;
    }
}