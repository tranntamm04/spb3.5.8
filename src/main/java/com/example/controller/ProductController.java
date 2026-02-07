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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProduct(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.findByIdWithPromotion(id);
        return product == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        productService.createProduct(dto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.updateProduct(dto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "search")
    public ResponseEntity<Page<Product>> searchByName(
            @RequestParam String search, Pageable pageable) {
        return ResponseEntity.ok(productService.getSearchItem(search, pageable));
    }

    @GetMapping(params = "typeId")
    public ResponseEntity<List<Product>> getByTypeId(@RequestParam int typeId) {
        return ResponseEntity.ok(productService.findByTypeId(typeId)
        );
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> importStock(@PathVariable int id, @RequestParam int quantity) {
        return productService.importStock(id, quantity)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<Rating>> getRatings(@PathVariable int id) {
        return ResponseEntity.ok(ratingService.findVisible(id)
        );
    }
}
