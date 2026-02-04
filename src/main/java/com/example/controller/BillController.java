package com.example.controller;

import com.example.dto.BillDTO;
import com.example.entity.Bill;
import com.example.entity.ContractDetail;
import com.example.service.BillService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/bill")
public class BillController {

    private final BillService billService;

    @GetMapping("/listBill")
    public ResponseEntity<Page<Bill>> getAllBill(Pageable pageable) {
        return billService.getAllBillResponse(pageable);
    }

    @DeleteMapping("/deleteBill/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable int id) {
        return billService.deleteBillResponse(id);
    }

    @PostMapping("/payment")
    public ResponseEntity<List<FieldError>> payment(
            @Valid @RequestBody BillDTO billDTO,
            BindingResult bindingResult) {
        return billService.createBillResponse(billDTO, bindingResult);
    }

    @GetMapping("/billDetail/{id}")
    public ResponseEntity<List<Bill>> getBillByCustomer(@PathVariable String id) {
        return billService.getBillByCustomerResponse(id);
    }

    @GetMapping("/xem/{id}")
    public ResponseEntity<List<ContractDetail>> getBillDetail(@PathVariable int id) {
        return billService.getBillDetailResponse(id);
    }

    @PutMapping("/duyet/{id}")
    public ResponseEntity<?> approveBill(@PathVariable int id) {
        return billService.approveBillResponse(id);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Page<Bill>> searchBill(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam String name) {
        return billService.searchBillResponse(name, pageable);
    }
}
