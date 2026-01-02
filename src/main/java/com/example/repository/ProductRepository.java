package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
        select p
        from Product p
        join p.productType pt
        where pt.nameType like %:t%
        order by p.idProduct
    """)
    Page<Product> searchTag(@Param("t") String t, Pageable pageable);

    @Query("""
        select p
        from Product p
        join p.productType pt
        where p.productName like %:itemSearch%
           or pt.nameType like %:itemSearch%
        order by p.idProduct
    """)
    Page<Product> searchItem(@Param("itemSearch") String itemSearch, Pageable pageable);
}

