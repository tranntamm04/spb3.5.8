package com.example.service;

public interface EmailService {

    void sendOtpEmail(String toEmail, String otp);

    void sendResetPasswordEmail(String toEmail, String resetLink);

    void sendHtmlEmail(String toEmail, String subject, String htmlContent);
}
