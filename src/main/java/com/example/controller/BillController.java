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
import com.example.dto.BillDTO;
import com.example.dto.ProductDTO;
import com.example.entity.*;
import com.example.service.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/bill")
@RestController
public class BillController {
    @Autowired
    BillService billService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ContractDetailService contractDetailService;

    @RequestMapping(value = "/listBill", method = RequestMethod.GET)
    public ResponseEntity<Page<Bill>> getAllBill(@PageableDefault(size = 10) Pageable pageable) {
        Page<Bill> bills = billService.getAllBill(pageable);
        if (bills.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteBill/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Bill> deleteProduct(@PathVariable("id") int id) {
        Bill bill = billService.findById(id);
        if (bill == null) {
            return new ResponseEntity<Bill>(HttpStatus.NOT_FOUND);
        }
        billService.deleteBill(id);
        return new ResponseEntity<Bill>(HttpStatus.OK);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity<List<FieldError>> createProduct(@RequestBody @Valid BillDTO billDTO,
            BindingResult bindingResult) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Customer customer = customerService.findByUser(billDTO.getIdCustomer());
        Bill bill = new Bill(LocalDate.now(), billDTO.getReceived(), billDTO.getPhone(),
                billDTO.getAddress(), billDTO.getPaymentMethods(), billDTO.getTotalMoney(), 1, customer);
        Bill bill1 = billService.saveBill(bill);
        for (ProductDTO p : billDTO.getObject()) {
            Product product = productService.findById(p.getIdProduct());
            BillProductKey billProductKey = new BillProductKey(bill1.getIdBill(), product.getIdProduct());
            ContractDetail contractDetail = new ContractDetail(billProductKey, bill1, product, p.getQuantity(),
                    product.getPrice());
            product.setQuantity(product.getQuantity() - p.getQuantity());
            productService.saveProduct(product);
            contractDetailService.save(contractDetail);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/billDetail/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Bill>> getAll(@PathVariable String id) {
        List<Bill> billList = billService.findByIdCustomer(id);
        if (billList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(billList, HttpStatus.OK);
    }

    @RequestMapping(value = "/xem/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ContractDetail>> getAllChiTiet(@PathVariable int id) {
        List<ContractDetail> contractDetailList = contractDetailService.findAll(id);
        if (contractDetailList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contractDetailList, HttpStatus.OK);
    }

    @RequestMapping(value = "/duyet/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Bill> duyet(@PathVariable int id) {
        Bill bill = billService.findById(id);
        if (bill == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bill.setStatus(2);
        billService.saveBill(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Page<Bill>> getSearchItem(@PageableDefault(size = 6) Pageable pageable,
            @RequestParam String name) {
        Page<Bill> bills = billService.getSearchItem(name, pageable);
        if (bills.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

}
