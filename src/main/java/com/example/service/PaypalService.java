package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaypalService {

    @Value("${paypal.return-url}")
    private String returnUrl;

    @Value("${paypal.cancel-url}")
    private String cancelUrl;

}