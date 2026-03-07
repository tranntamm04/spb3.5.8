package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/paypal")
@CrossOrigin("*")
public class PayPalController {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.base.url}")
    private String baseUrl;

    @Value("${paypal.return-url}")
    private String returnUrl;

    @Value("${paypal.cancel-url}")
    private String cancelUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getAccessToken() {

        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + auth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/v1/oauth2/token",
                HttpMethod.POST,
                request,
                Map.class
        );

        return (String) response.getBody().get("access_token");
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam Double amount) {

        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("intent", "CAPTURE");

        Map<String, Object> amountMap = new HashMap<>();
        amountMap.put("currency_code", "USD");
        amountMap.put("value", String.format("%.2f", amount));

        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", amountMap);

        body.put("purchase_units", List.of(purchaseUnit));

        Map<String, String> appContext = new HashMap<>();
        appContext.put("return_url", returnUrl);
        appContext.put("cancel_url", cancelUrl);

        body.put("application_context", appContext);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseUrl + "/v2/checkout/orders",
                request,
                Map.class
        );

        List<Map<String, Object>> links =
                (List<Map<String, Object>>) response.getBody().get("links");

        for (Map<String, Object> link : links) {
            if ("approve".equals(link.get("rel"))) {
                return (String) link.get("href");
            }
        }

        return "Error creating order";
    }

    @PostMapping("/capture")
    public String captureOrder(@RequestParam String token) {

        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{}", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/v2/checkout/orders/" + token + "/capture",
                HttpMethod.POST,
                request,
                Map.class
        );

        if ("COMPLETED".equals(response.getBody().get("status"))) {
            return "Payment Successful";
        }

        return "Payment Failed";
    }
}