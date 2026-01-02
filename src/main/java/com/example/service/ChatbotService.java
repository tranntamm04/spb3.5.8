package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ChatbotService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=%s";

    public ChatbotService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String askGemini(String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Xin chào! Tôi có thể tư vấn điện thoại cho bạn?";
        }
        
        try {
            List<Product> allProducts = productRepository.findAll();
            
            // Ưu tiên xử lý cục bộ trước
            String quickResponse = getQuickResponse(userMessage, allProducts);
            if (quickResponse != null) {
                return quickResponse;
            }
            
            // Nếu không có response nhanh, gọi Gemini
            String prompt = createPrompt(userMessage, allProducts);
            String geminiResponse = callGeminiAPI(prompt);
            
            return geminiResponse != null ? geminiResponse : getFallbackResponse(userMessage, allProducts);
            
        } catch (Exception e) {
            return getFallbackResponse(userMessage, productRepository.findAll());
        }
    }

    private String getQuickResponse(String userMessage, List<Product> products) {
        String message = userMessage.toLowerCase().trim();
        
        // Xử lý các câu hỏi phổ biến cục bộ
        if (message.matches(".*(xin chào|hello|hi|chào).*")) {
            return "Chào bạn! Tôi có thể tư vấn điện thoại cho bạn?";
        }
        
        if (message.matches(".*(có gì|sản phẩm|mẫu nào).*")) {
            return getProductList(products);
        }
        
        if (message.matches(".*(iphone|ip).*")) {
            return getIphoneRecommendation(products);
        }
        
        if (message.matches(".*(game|chơi game).*")) {
            return getGamingRecommendation(products);
        }
        
        if (message.matches(".*(pin|trâu).*")) {
            return getBatteryRecommendation(products);
        }
        
        // Xử lý ngân sách
        if (message.matches(".*[0-9].*triệu.*")) {
            int budget = extractBudget(message);
            return getBudgetRecommendation(budget, products);
        }
        
        return null;
    }

    private String getProductList(List<Product> products) {
        if (products.isEmpty()) return "Hiện chưa có sản phẩm nào.";
        
        StringBuilder sb = new StringBuilder("Các sản phẩm hiện có:\n");
        for (int i = 0; i < Math.min(products.size(), 3); i++) {
            Product p = products.get(i);
            sb.append(String.format("- %s: %dVND\n", p.getProductName(), p.getPrice()));
        }
        return sb.toString();
    }

    private String getIphoneRecommendation(List<Product> products) {
        List<Product> iphones = products.stream()
                .filter(p -> p.getProductName().toLowerCase().contains("iphone"))
                .collect(Collectors.toList());
                
        if (iphones.isEmpty()) return "Hiện không có iPhone trong kho.";
        
        Product bestIphone = iphones.get(0);
        return String.format("Bạn có thể xem %s - Giá: %dVND - RAM: %s - ROM: %s", 
                bestIphone.getProductName(), bestIphone.getPrice(), bestIphone.getRam(), bestIphone.getRom());
    }

    private String getGamingRecommendation(List<Product> products) {
        if (products.isEmpty()) return "Hiện không có sản phẩm phù hợp.";
        
        // Tìm sản phẩm có RAM lớn nhất
        Product bestForGaming = products.stream()
                .filter(p -> p.getRam() != null && p.getRam().matches(".*[0-9].*GB.*"))
                .sorted((p1, p2) -> {
                    int ram1 = extractRam(p1.getRam());
                    int ram2 = extractRam(p2.getRam());
                    return ram2 - ram1;
                })
                .findFirst()
                .orElse(products.get(0));
                
        return String.format("Máy chơi game tốt: %s - RAM %s - Chip %s - Giá %dVND", 
                bestForGaming.getProductName(), bestForGaming.getRam(), 
                bestForGaming.getChip(), bestForGaming.getPrice());
    }

    private String getBatteryRecommendation(List<Product> products) {
        if (products.isEmpty()) return "Hiện không có sản phẩm phù hợp.";
        
        Product bestBattery = products.get(0);
        for (Product p : products) {
            if (p.getPin() != null && p.getPin().contains("mAh")) {
                bestBattery = p;
                break;
            }
        }
        
        return String.format("Máy pin trâu: %s - Pin %s - Giá %dVND", 
                bestBattery.getProductName(), bestBattery.getPin(), bestBattery.getPrice());
    }

    private String getBudgetRecommendation(int budget, List<Product> products) {
        List<Product> affordable = products.stream()
                .filter(p -> p.getPrice() <= budget)
                .collect(Collectors.toList());
                
        if (affordable.isEmpty()) {
            return String.format("Với %dVND, hiện không có sản phẩm phù hợp. Bạn có thể xem các model giá thấp hơn.", budget);
        }
        
        Product bestMatch = affordable.get(0);
        return String.format("Với %dVND, bạn có thể xem %s - RAM %s - ROM %s - Giá %dVND", 
                budget, bestMatch.getProductName(), bestMatch.getRam(), 
                bestMatch.getRom(), bestMatch.getPrice());
    }

    private int extractBudget(String message) {
        try {
            String numbers = message.replaceAll("[^0-9]", "");
            if (!numbers.isEmpty()) {
                int amount = Integer.parseInt(numbers);
                return amount * 1000000; // triệu VND
            }
        } catch (Exception e) {
            // Ignore
        }
        return 15000000; // mặc định 15 triệu
    }

    private int extractRam(String ramText) {
        try {
            if (ramText != null) {
                String numbers = ramText.replaceAll("[^0-9]", "");
                if (!numbers.isEmpty()) {
                    return Integer.parseInt(numbers);
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return 0;
    }

    private String createPrompt(String userMessage, List<Product> products) {
        StringBuilder productInfo = new StringBuilder();
        
        for (Product p : products) {
            productInfo.append(String.format("%s | Giá: %dVND | RAM: %s | ROM: %s | Chip: %s",
                    p.getProductName(), p.getPrice(), p.getRam(), p.getRom(), p.getChip()));
            
            if (p.getPin() != null) {
                productInfo.append(" | Pin: ").append(p.getPin());
            }
            productInfo.append("\n");
        }

        return "Bạn là nhân viên bán điện thoại. Trả lời NGẮN GỌN (1-2 câu).\n\n" +
               "Khách hỏi: " + userMessage + "\n\n" +
               "Sản phẩm:\n" + productInfo.toString() + "\n" +
               "Trả lời ngắn gọn:";
    }

    private String callGeminiAPI(String prompt) {
        try {
            String url = String.format(GEMINI_URL, apiKey);

            String requestJson = String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}",
                prompt.replace("\"", "\\\"").replace("\n", "\\n")
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = mapper.readTree(response.getBody());
                return root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText();
            }
            
        } catch (Exception e) {
            System.out.println("Gemini API error: " + e.getMessage());
        }
        return null;
    }

    private String getFallbackResponse(String userMessage, List<Product> products) {
        String message = userMessage.toLowerCase();
        
        if (message.contains("iphone") || message.contains("ip")) {
            return getIphoneRecommendation(products);
        }
        
        if (message.matches(".*[0-9].*")) {
            int budget = extractBudget(message);
            return getBudgetRecommendation(budget, products);
        }
        
        return "Tôi có thể tư vấn về điện thoại cho bạn. Bạn muốn biết về sản phẩm nào?";
    }
}