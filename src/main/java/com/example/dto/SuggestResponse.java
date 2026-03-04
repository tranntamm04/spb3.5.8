package com.example.dto;

import com.example.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SuggestResponse {

    private List<String> keywords;
    private List<Product> products;

}