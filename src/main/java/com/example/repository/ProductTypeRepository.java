package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
}
