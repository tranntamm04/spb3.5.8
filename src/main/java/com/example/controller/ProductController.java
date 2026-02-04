package com.example.controller;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.entity.Rating;
import com.example.service.ProductService;
import com.example.service.RatingService;
import jakarta.validation.*;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final RatingService ratingService;

    @GetMapping("/listProduct")
    public ResponseEntity<Page<Product>> getAllProduct(
            @PageableDefault(size = 10) Pageable pageable) {
        return productService.getAllProductResponse(pageable);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        return productService.deleteProductResponse(id);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<List<FieldError>> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult) {
        return productService.createProductResponse(productDTO, bindingResult);
    }

    @GetMapping("/viewProduct/{id}")
    public ResponseEntity<ProductDTO> detailProduct(@PathVariable int id) {
        return productService.getDetailProductResponse(id);
    }

    @GetMapping("/viewProductPromotion/{id}")
    public ResponseEntity<Product> viewProductPromotion(@PathVariable int id) {
        return productService.getProductPromotionResponse(id);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult,
            @PathVariable int id) {
        return productService.updateProductResponse(productDTO, bindingResult, id);
    }

    @GetMapping("/searchTag")
    public ResponseEntity<Page<Product>> getSearchTag(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam("t") String t) {
        return productService.searchTagResponse(t, pageable);
    }

    @GetMapping({"/searchItem", "/searchItem2"})
    public ResponseEntity<Page<Product>> getSearchItem(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam("itemSearch") String itemSearch) {
        return productService.searchItemResponse(itemSearch, pageable);
    }

    @GetMapping("/listHomeProduct")
    public ResponseEntity<Page<Product>> getAllProductHome(
            @PageableDefault(size = 10) Pageable pageable) {
        return productService.getAllProductResponse(pageable);
    }

    @RequestMapping(value = "/getBinhLuan/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Rating>> getAllBinhLuan(@PathVariable int id) {
        List<Rating> rating = ratingService.findVisible(id);
        if (rating.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rating, HttpStatus.OK); }

    @PatchMapping("/nhapkho/{id}")
    public ResponseEntity<?> importStock(
            @PathVariable int id,
            @RequestParam int quantity) {
        return productService.importStockResponse(id, quantity);
    }
}

