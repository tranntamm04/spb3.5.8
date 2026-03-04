package com.example.service.impl;

import com.example.entity.Product;
import lombok.Data;

@Data
public class ConversationMemory {

    private Integer lastPrice;
    private String lastBrand;
    private Product lastProduct;
}