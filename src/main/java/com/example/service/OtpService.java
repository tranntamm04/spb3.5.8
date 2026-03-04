package com.example.service;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.PasswordOtp;
import com.example.repository.CustomerRepository;
import com.example.repository.PasswordOtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final PasswordOtpRepository otpRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void sendOtp(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email chưa đăng ký"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        PasswordOtp passwordOtp = otpRepository.findByEmail(email).orElse(new PasswordOtp());

        passwordOtp.setEmail(email);
        passwordOtp.setOtp(otp);
        passwordOtp.setExpireTime(LocalDateTime.now().plusMinutes(5));
        passwordOtp.setVerified(false);

        otpRepository.save(passwordOtp);

        emailService.sendOtpEmail(email, otp);
    }

    public void verifyOtp(String email, String otp) {
        PasswordOtp passwordOtp = otpRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy OTP"));
        if (passwordOtp.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP đã hết hạn");
        }
        if (!passwordOtp.getOtp().equals(otp)) {
            throw new RuntimeException("OTP không đúng");
        }
        passwordOtp.setVerified(true);
        otpRepository.save(passwordOtp);
    }

    public void resetPassword(String email, String newPassword) {
        PasswordOtp passwordOtp = otpRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("OTP không tồn tại"));
        if (!passwordOtp.isVerified()) {
            throw new RuntimeException("OTP chưa được xác thực");
        }

        validatePassword(newPassword);
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Account account = customer.getAccount();

        if (passwordEncoder.matches(newPassword, account.getPassword())) {
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        account.setPassword(passwordEncoder.encode(newPassword));
        otpRepository.delete(passwordOtp);
    }

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,}$";

    private void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new RuntimeException("Mật khẩu tối thiểu 6 ký tự, gồm 1 chữ hoa, 1 số và 1 ký tự đặc biệt");
        }
    }
}
