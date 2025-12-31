package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("select c from  Customer  c where c.account.userName like %:user%")
    Customer findByUser(@Param("user") String user);

    @Query("select c from  Customer  c where c.idCustomer like %:name% or c.surname like %:name% or c.name like %:name% or c.address like %:name%")
    Page<Customer> findByAll(@Param("name") String name, Pageable pageable);
}
