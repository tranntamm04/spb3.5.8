package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.entity.Bill;

import java.util.List;

public interface BillService {
    Page<Bill> getAllBill(Pageable pageable);
    Bill findById(int id);
    void deleteBill(int id);
    Bill saveBill(Bill bill);
    List<Bill> findByIdCustomer(String id);
    Page<Bill> getSearchItem(String itemSearch, Pageable pageable);
}
