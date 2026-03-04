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
        @Modifying
        @Query(
                value = """
            UPDATE product
            SET quantity = quantity - :qty,
                sold = sold + :qty
            WHERE id_product = :id
              AND quantity >= :qty
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
          and p.productType.idType = 7
        order by p.sold desc
    """)
        List<Product> findAccessoryForChatbot();

        @Query("""
        select p
        from Product p
        where p.productType.idType = :typeId
          and p.status = 1
        order by p.idProduct
    """)
        List<Product> findByTypeId(@Param("typeId") int typeId);

        @Query("""
                select distinct p.productName
                from Product p
                where lower(p.productName) like lower(concat(:keyword,'%'))
            """)
        List<String> suggestKeyword(@Param("keyword") String keyword);

        @Query("""
                select p
                from Product p
                where lower(p.productName) like lower(concat(:keyword,'%'))
                  and p.status = 1
                order by p.sold desc
            """)
        List<Product> suggestProduct(@Param("keyword") String keyword, Pageable pageable);
    }


