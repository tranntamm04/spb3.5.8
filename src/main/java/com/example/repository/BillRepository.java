package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Bill;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query("select b from Bill b where b.customer.idCustomer like %:id%")
    List<Bill> findByList(@Param("id") String id);

    @Query("select b from Bill b where b.customer.idCustomer like %:id% ")
    Page<Bill> findByName(@Param("id") String id, Pageable pageable);

    @Query("select b from Bill b where b.status = :status")
    Page<Bill> findByStatus(@Param("status") int status, Pageable pageable);

    @Query("select coalesce(sum(b.totalMoney), 0) from Bill b where b.status = 2")
    Long getTotalRevenue();

}
