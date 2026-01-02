package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.dto.EvaluateDTO;
import com.example.dto.ProductDTO;
import com.example.entity.*;
import com.example.service.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    EvaluateService evaluateService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EvaluatesService evaluatesService;

    @RequestMapping(value = "/listPromotion", method = RequestMethod.GET)
    public ResponseEntity<List<Promotion>> getAllPromotion() {
        List<Promotion> promotions = promotionService.findAll();
        if (promotions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @RequestMapping(value = "/listProduct", method = RequestMethod.GET)
    public ResponseEntity<Page<Product>> getAllProduct(@PageableDefault(size = 6) Pageable pageable) {
        Page<Product> products = productService.getAllProduct(pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        productService.deleteCustomer(id);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public ResponseEntity<List<FieldError>> createProduct(@RequestBody @Valid ProductDTO productDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Promotion promotion = promotionService.findById(productDTO.getIdPromotion());
        ProductType productType = productTypeService.findById(productDTO.getIdType());
        String img = productDTO.getAvt();
        img = img.substring(12, img.length());
        System.out.println(img);
        Product product = new Product(productDTO.getProductName(), img, productDTO.getPrice(), 1, 
        productDTO.getQuantity(), 0, 4, productDTO.getScreen(), productDTO.getHdh(), 
        productDTO.getCamTr(), productDTO.getCamSau(), productDTO.getRam(), productDTO.getRom(), 
        productDTO.getChip(), productDTO.getPin(), promotion, productType);
        productService.saveProduct(product);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/viewProduct/{id}")
    public ResponseEntity<ProductDTO> detailProduct(@PathVariable int id) {
        Product product = this.productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductDTO productDTO = new ProductDTO(product.getIdProduct(), product.getProductName(), 
        product.getAvt(), product.getPrice(),  product.getStatus(), product.getQuantity(), 
        product.getNumOfReview(), product.getNumOfStar(), product.getScreen(), product.getHdh(), 
        product.getCamTr(), product.getCamSau(), product.getRam(), product.getRom(), product.getChip(), 
        product.getPin(), product.getProductType().getIdType(), product.getPromotion().getIdPromotion(),
        product.getProductType().getNameType());

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ProductDTO> updateEmployee(@Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult, @PathVariable int id) {
        if (!bindingResult.hasErrors()) {
            productService.update(productDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/searchTag")
    public ResponseEntity<Page<Product>> getSearchTag(@PageableDefault(size = 6) Pageable pageable,
            @RequestParam("t") String t) {
        Page<Product> products = productService.getSearchTag(t, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/searchItem2")
    public ResponseEntity<Page<Product>> getSearchItem2(@PageableDefault(size = 6) Pageable pageable,
            @RequestParam("itemSearch") String itemSearch) {
        Page<Product> products = productService.getSearchItem(itemSearch, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/searchItem")
    public ResponseEntity<Page<Product>> getSearchItem(@PageableDefault(size = 9) Pageable pageable,
            @RequestParam("itemSearch") String itemSearch) {
        Page<Product> products = productService.getSearchItem(itemSearch, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/listHomeProduct", method = RequestMethod.GET)
    public ResponseEntity<Page<Product>> getAllProductHome(@PageableDefault(size = 9) Pageable pageable) {
        Page<Product> products = productService.getAllProduct(pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/createEvaluate")
    public ResponseEntity<EvaluateDTO> updateEmployee(@Valid @RequestBody EvaluateDTO evaluateDTO,
            BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Product product = productService.findById(evaluateDTO.getIdProduct());
            Customer customer = customerService.findById(evaluateDTO.getIdCustomer());
            EvaluateKey evaluateKey = new EvaluateKey(evaluateDTO.getIdCustomer(), evaluateDTO.getIdProduct());
            Evaluates evaluates = new Evaluates(evaluateKey, product, customer, evaluateDTO.getNumberOfStar(),
                    evaluateDTO.getComment(), LocalDate.now());
            int a = product.getNumOfReview();
            int b = ++a;
            product.setNumOfReview(b);
            productService.saveProduct(product);
            evaluatesService.save(evaluates);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getBinhLuan/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Evaluates>> getAllBinhLuan(@PathVariable int id) {
        List<Evaluates> evaluates = evaluatesService.findAll(id);
        if (evaluates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(evaluates, HttpStatus.OK);
    }

}
