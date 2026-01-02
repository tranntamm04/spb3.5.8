package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.entity.ProductType;
import com.example.entity.Promotion;
import com.example.repository.ProductRepository;
import com.example.repository.ProductTypeRepository;
import com.example.repository.PromotionRepository;
import com.example.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

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
        this.productRepository.save(product);
    }

    @Override
    public void update(ProductDTO productDTO, int id) {
        Product product = productRepository.findById(id).orElse(null);
        Promotion promotion = promotionRepository.findById(productDTO.getIdPromotion()).orElse(null);
        ProductType productType = productTypeRepository.findById(productDTO.getIdType()).orElse(null);
        if (product != null) {
            product.setPromotion(promotion);
            product.setProductType(productType);
            product.setProductName(productDTO.getProductName());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setAvt(productDTO.getAvt());
            product.setScreen(productDTO.getScreen());
            product.setHdh(productDTO.getHdh());
            product.setCamTr(productDTO.getCamTr());
            product.setCamSau(productDTO.getCamSau());
            product.setRam(productDTO.getRam());
            product.setRom(productDTO.getRom());
            product.setChip(productDTO.getChip());
            product.setPin(productDTO.getPin());
            // promotion.setIdPromotion(productDTO.getIdPromotion());
            // productType.setIdType(productDTO.getIdType());
            productRepository.save(product);
        }
    }

    @Override
    public Page<Product> getSearchTag(String t, Pageable pageable) {
        return this.productRepository.searchTag(t, pageable);
    }

    @Override
    public Page<Product> getSearchItem(String itemSearch, Pageable pageable) {
        return this.productRepository.searchItem(itemSearch, pageable);
    }
}
