package com.example.service.impl;

import com.example.dto.BillDTO;
import com.example.dto.ProductDTO;
import com.example.entity.*;
import com.example.repository.BillRepository;
import com.example.repository.ContractDetailRepository;
import com.example.repository.ProductRepository;
import com.example.service.BillService;
import com.example.service.CustomerService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ContractDetailRepository contractDetailRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final ProductService productService;

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
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> findByIdCustomer(String id) {
        return billRepository.findByList(id);
    }

    @Override
    public Page<Bill> getSearchItem(String itemSearch, Pageable pageable) {
        return billRepository.findByName(itemSearch, pageable);
    }

    private int getFinalPrice(Product product) {
        int price = product.getPrice();
        Promotion promotion = product.getPromotion();

        if (promotion == null) return price;

        if ("PERCENT".equalsIgnoreCase(promotion.getTypePromotion())) {
            return (int) Math.round(
                    price * (1 - promotion.getPromotionalValue() / 100.0));
        }

        if ("MONEY".equalsIgnoreCase(promotion.getTypePromotion())) {
            return Math.max(price - (int) promotion.getPromotionalValue(), 0);
        }

        return price;
    }

    @Override
    public ResponseEntity<Page<Bill>> getAllBillResponse(Pageable pageable) {
        Page<Bill> bills = getAllBill(pageable);
        return bills.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(bills);
    }

    @Override
    public ResponseEntity<?> deleteBillResponse(int id) {
        Bill bill = findById(id);
        if (bill == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        deleteBill(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<FieldError>> createBillResponse(
            BillDTO billDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        Customer customer =
                customerService.findByUser(billDTO.getIdCustomer());

        Bill bill = new Bill(
                LocalDateTime.now(),
                billDTO.getReceived(),
                billDTO.getPhone(),
                billDTO.getAddress(),
                billDTO.getPaymentMethods(),
                billDTO.getTotalMoney(),
                1,
                customer
        );

        Bill savedBill = saveBill(bill);

        for (ProductDTO p : billDTO.getObject()) {
            Product product = productService.findById(p.getIdProduct());
            int finalPrice = getFinalPrice(product);

            BillProductKey key = new BillProductKey(
                    savedBill.getIdBill(),
                    product.getIdProduct());

            ContractDetail detail = new ContractDetail(
                    key,
                    savedBill,
                    product,
                    p.getQuantity(),
                    finalPrice
            );

            contractDetailRepository.save(detail);
        }

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Bill>> getBillByCustomerResponse(String id) {
        List<Bill> bills = findByIdCustomer(id);
        return bills.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(bills);
    }

    @Override
    public ResponseEntity<List<ContractDetail>> getBillDetailResponse(int billId) {
        List<ContractDetail> details =
                contractDetailRepository.findByMaHD(billId);
        return details.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(details);
    }

    @Override
    @Transactional
    public ResponseEntity<?> approveBillResponse(int id) {
        Bill bill = findById(id);
        if (bill == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        approveBill(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Bill>> searchBillResponse(
            String name, Pageable pageable) {

        Page<Bill> bills = getSearchItem(name, pageable);
        return bills.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(bills);
    }

    @Override
    @Transactional
    public void approveBill(int billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        List<ContractDetail> details =
                contractDetailRepository.findByMaHD(billId);

        for (ContractDetail cd : details) {
            productRepository.updateStock(
                    cd.getProduct().getIdProduct(),
                    cd.getQuantity()
            );
        }

        bill.setStatus(2);
    }
}
