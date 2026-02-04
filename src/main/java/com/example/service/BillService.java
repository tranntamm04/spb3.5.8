package com.example.service;

import com.example.dto.BillDTO;
import com.example.entity.Bill;
import com.example.entity.ContractDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public interface BillService {

    Page<Bill> getAllBill(Pageable pageable);
    Bill findById(int id);
    void deleteBill(int id);
    Bill saveBill(Bill bill);
    List<Bill> findByIdCustomer(String id);
    Page<Bill> getSearchItem(String itemSearch, Pageable pageable);
    void approveBill(int billId);

    ResponseEntity<Page<Bill>> getAllBillResponse(Pageable pageable);
    ResponseEntity<?> deleteBillResponse(int id);
    ResponseEntity<List<FieldError>> createBillResponse(
            BillDTO billDTO, BindingResult bindingResult);
    ResponseEntity<List<Bill>> getBillByCustomerResponse(String id);
    ResponseEntity<List<ContractDetail>> getBillDetailResponse(int billId);
    ResponseEntity<?> approveBillResponse(int id);
    ResponseEntity<Page<Bill>> searchBillResponse(String name, Pageable pageable);
}
