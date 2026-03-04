package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "inventory_history")
public class InventoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantityChanged;
    private String createdBy;
    private LocalDateTime createdAt;

    private String note;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
