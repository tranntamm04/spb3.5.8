package com.example.controller;

import com.example.service.OtpService;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ThymeleafController {

    private final OtpService otpService;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam String email, Model model) {

        try {
            otpService.sendOtp(email);
            model.addAttribute("email", email);
            return "verify-otp";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        try {
            otpService.verifyOtp(email, otp);
            model.addAttribute("email", email);
            return "reset-password";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            return "verify-otp";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            otpService.resetPassword(email, password);
            return "redirect:http://localhost:4200/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            return "reset-password";
        }
    }
}
