package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.entity.Bill;
import com.example.repository.BillRepository;
import com.example.service.BillService;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Override
    public Page<Bill> getAllBill(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Bill findById(int id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBill(int id) {
        billRepository.deleteById(id);
    }

    @Override
    public Bill saveBill(Bill bill) {
        return this.billRepository.save(bill);
    }

    @Override
    public List<Bill> findByIdCustomer(String id) {
        return this.billRepository.findByList(id);
    }

    @Override
    public Page<Bill> getSearchItem(String itemSearch, Pageable pageable) {
        return this.billRepository.findByName(itemSearch, pageable);
    }
}
