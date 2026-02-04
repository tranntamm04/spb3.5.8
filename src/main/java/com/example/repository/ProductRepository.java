package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Product;

import java.util.List;


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

    @Query("""
    select p
    from Product p
    left join fetch p.promotion
    left join fetch p.productType
    where p.idProduct = :id
""")
Product findByIdWithPromotion(@Param("id") Integer id);

@Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        value = """
            UPDATE product
            SET quantity = quantity - :qty,
                sold = sold + :qty
            WHERE id_product = :id
        """,
        nativeQuery = true
    )
    int updateStock(
        @Param("id") Integer id,
        @Param("qty") int qty
    );

    List<Product> findTop5ByStatusOrderBySoldDesc(int status);

    List<Product> findTop5ByStatusOrderByNumOfStarDesc(int status);

    @Query("""
    select p
    from Product p
    where p.status = 1
      and p.productType.idType <> 7
      and p.price between :min and :max
    order by p.sold desc
""")
    List<Product> findPhoneForChatbot(
            @Param("min") int min,
            @Param("max") int max
    );

    @Query("""
    select p
    from Product p
    where p.status = 1
      and p.productType.idType = 7
    order by p.sold desc
""")
    List<Product> findAccessoryForChatbot();

}


