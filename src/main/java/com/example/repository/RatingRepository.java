package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Rating;
import com.example.entity.RatingKey;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    @Query("select e from Rating e where e.product.idProduct = :id")
    List<Rating> findByIdPro(@Param("id") int id);

    List<Rating> findByStatus(int status);

    @Query("""
        select e from Rating e
        where e.product.idProduct = :id
          and e.status = 1
    """)
    List<Rating> findVisibleByProduct(@Param("id") int id);
}
