package com.example.service;

import com.example.dto.BillDTO;
import com.example.entity.Bill;
import com.example.entity.ContractDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillService {

    Page<Bill> findAll(Pageable pageable);
    Bill findById(int id);
    void deleteById(int id);

    Bill createBill(BillDTO billDTO);
    List<Bill> findByCustomerId(String customerId);
    List<ContractDetail> findBillDetails(int billId);

    void approveBill(int billId);
    Page<Bill> searchByName(String name, Pageable pageable);
}

