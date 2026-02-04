package com.example.service.impl;

import com.example.dto.RatingDTO;
import com.example.entity.Customer;
import com.example.entity.Product;
import com.example.service.CustomerService;
import com.example.service.ProductService;
import lombok.*;
import org.springframework.stereotype.Service;
import com.example.entity.Rating;
import com.example.entity.RatingKey;
import com.example.repository.RatingRepository;
import com.example.service.RatingService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    public void save(Rating evaluates) {
        this.ratingRepository.save(evaluates);
    }

    @Override
    public List<Rating> findAll(int id) {
        return ratingRepository.findByIdPro(id);
    }

    @Override
    public List<Rating> findVisible(int id) {
        return ratingRepository.findVisibleByProduct(id);
    }
    @Override
    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public void delete(String customerId, int productId) {
        RatingKey key = new RatingKey(customerId, productId);
        ratingRepository.deleteById(key);
    }

    @Override
    public void updateStatus(String customerId, int productId, int status) {
        RatingKey key = new RatingKey(customerId, productId);
        ratingRepository.findById(key).ifPresent(rating -> {
            rating.setStatus(status);
            ratingRepository.save(rating);
        });
    }

    @Override
    public void createRating(RatingDTO ratingDTO) {
        Product product = productService.findById(ratingDTO.getIdProduct());
        Customer customer = customerService.findById(ratingDTO.getIdCustomer());
        RatingKey key = new RatingKey(ratingDTO.getIdCustomer(), ratingDTO.getIdProduct());
        Rating rating = new Rating(key, product, customer, ratingDTO.getNumberOfStar(), ratingDTO.getComment(),
                LocalDateTime.now());
        rating.setStatus(1);
        ratingRepository.save(rating);
        updateProductRatingSummary(product, ratingDTO.getNumberOfStar());
    }

    private void updateProductRatingSummary(Product product, int newStar) {
        int oldCount = product.getNumOfReview();
        double oldAvg = product.getNumOfStar();
        int newCount = oldCount + 1;
        double newAvg = (oldAvg * oldCount + newStar) / newCount;
        newAvg = Math.round(newAvg * 10.0) / 10.0;
        product.setNumOfReview(newCount);
        product.setNumOfStar(newAvg);
        productService.saveProduct(product);
    }
}
