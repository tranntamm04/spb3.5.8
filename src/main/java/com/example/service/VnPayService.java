package com.example.service;

import com.example.config.VnPayConfig;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {

    private final VnPayConfig config;

    public VnPayService(VnPayConfig config) {
        this.config = config;
    }

    public String createPaymentUrl(Long amount) throws Exception {

        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", config.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", vnp_TxnRef);
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang");
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", config.getReturnUrl());
        vnpParams.put("vnp_IpAddr", "127.0.0.1");
        vnpParams.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = vnpParams.get(fieldName);

            hashData.append(fieldName).append('=')
                    .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                    .append('=')
                    .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

            if (i < fieldNames.size() - 1) {
                hashData.append('&');
                query.append('&');
            }
        }

        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec =
                new SecretKeySpec(config.getSecretKey().getBytes(), "HmacSHA512");

        hmac512.init(secretKeySpec);
        byte[] hashBytes = hmac512.doFinal(hashData.toString().getBytes());
        String secureHash = bytesToHex(hashBytes);

        query.append("&vnp_SecureHash=").append(secureHash);

        return config.getPayUrl() + "?" + query.toString();
    }

    public boolean verifyReturn(Map<String, String> params) throws Exception {

        String vnp_SecureHash = params.remove("vnp_SecureHash");

        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = params.get(fieldName);

            hashData.append(fieldName).append('=')
                    .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

            if (i < fieldNames.size() - 1) {
                hashData.append('&');
            }
        }

        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec =
                new SecretKeySpec(config.getSecretKey().getBytes(), "HmacSHA512");

        hmac512.init(secretKeySpec);
        byte[] hashBytes = hmac512.doFinal(hashData.toString().getBytes());
        String calculatedHash = bytesToHex(hashBytes);

        return calculatedHash.equals(vnp_SecureHash);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}