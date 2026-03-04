package com.example.repository;

import com.example.entity.InventoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Long> {
    @Query("""
      select h from InventoryHistory h
      order by h.createdAt desc
    """)
    List<InventoryHistory> findHistory();

}
