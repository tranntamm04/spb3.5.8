package com.example.controller;

import com.example.dto.BillDTO;
import com.example.entity.Bill;
import com.example.entity.ContractDetail;
import com.example.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/bill")
public class BillController {

    private final BillService billService;

    @GetMapping("/listBill")
    public ResponseEntity<Page<Bill>> getAllBill(Pageable pageable) {
        Page<Bill> bills = billService.findAll(pageable);
        return bills.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bills);
    }

    @DeleteMapping("/deleteBill/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable int id) {
        billService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment( @Valid @RequestBody BillDTO billDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(406).body(bindingResult.getFieldErrors());
        }
        billService.createBill(billDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/billDetail/{id}")
    public ResponseEntity<List<Bill>> getBillByCustomer(@PathVariable String id) {
        List<Bill> bills = billService.findByCustomerId(id);
        return bills.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bills);
    }

    @GetMapping("/xem/{id}")
    public ResponseEntity<List<ContractDetail>> getBillDetail(@PathVariable int id) {
        List<ContractDetail> details =  billService.findBillDetails(id);
        return details.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(details);
    }

    @PutMapping("/duyet/{id}")
    public ResponseEntity<?> approveBill(@PathVariable int id) {
        billService.approveBill(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Page<Bill>> searchBill(@RequestParam String name, Pageable pageable) {
        Page<Bill> bills = billService.searchByName(name, pageable);
        return bills.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bills);
    }
}
