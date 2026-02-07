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
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Bill> findAll(Pageable pageable) {

        return billRepository.findAll(pageable);
    }

    @Override
    public Bill findById(int id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Override
    public void deleteById(int id) {

        billRepository.deleteById(id);
    }

    @Override
    public List<Bill> findByCustomerId(String customerId) {

        return billRepository.findByList(customerId);
    }

    @Override
    public Page<Bill> searchByName(String name, Pageable pageable) {

        return billRepository.findByName(name, pageable);
    }

    @Override
    @Transactional
    public Bill createBill(BillDTO billDTO) {

        Customer customer = customerService.findByUser(billDTO.getIdCustomer());
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

        Bill savedBill = billRepository.save(bill);
        for (ProductDTO p : billDTO.getObject()) {
            Product product = productService.findById(p.getIdProduct());
            ContractDetail detail = new ContractDetail(
                    new BillProductKey(
                            savedBill.getIdBill(),
                            product.getIdProduct()
                    ),
                    savedBill,
                    product,
                    p.getQuantity(),
                    getFinalPrice(product)
            );
            contractDetailRepository.save(detail);
        }
        return savedBill;
    }

    @Override
    public List<ContractDetail> findBillDetails(int billId) {
        return contractDetailRepository.findByMaHD(billId);
    }

    @Override
    @Transactional
    public void approveBill(int billId) {
        Bill bill = findById(billId);
        if (bill.getStatus() == 2) {
            throw new RuntimeException("Bill đã được duyệt");
        }
        List<ContractDetail> details = contractDetailRepository.findByMaHD(billId);
        for (ContractDetail cd : details) {
            productRepository.updateStock(cd.getProduct().getIdProduct(), cd.getQuantity()
            );
        }
        bill.setStatus(2);
        billRepository.save(bill);
    }

    private int getFinalPrice(Product product) {
        int price = product.getPrice();
        Promotion promotion = product.getPromotion();
        if (promotion == null) return price;
        if ("PERCENT".equalsIgnoreCase(promotion.getTypePromotion())) {
            return (int) Math.round(price * (1 - promotion.getPromotionalValue() / 100.0));
        }
        if ("MONEY".equalsIgnoreCase(promotion.getTypePromotion())) {
            return Math.max(price - (int) promotion.getPromotionalValue(), 0);
        }
        return price;
    }
}
