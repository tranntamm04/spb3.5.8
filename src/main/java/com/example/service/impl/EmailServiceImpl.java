package com.example.service.impl;

import com.example.config.MyConstants;
import com.example.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MyConstants.MAIL_USERNAME);
            message.setTo(toEmail);
            message.setSubject("Mã OTP xác thực tài khoản");

            message.setText(
                    "Xin chào,\n\n"
                            + "Mã OTP của bạn là: " + otp + "\n"
                            + "Mã có hiệu lực trong 5 phút.\n\n"
                            + "Vui lòng không chia sẻ mã này cho bất kỳ ai."
            );

            mailSender.send(message);

            logger.info("Send OTP mail success to {}", toEmail);

        } catch (Exception e) {
            logger.error("Send OTP mail failed", e);
            throw new RuntimeException("Không thể gửi email OTP");
        }
    }

    @Override
    public void sendResetPasswordEmail(String toEmail, String resetLink) {

        String subject = "Yêu cầu đặt lại mật khẩu";

        String content =
                "<h3>Đặt lại mật khẩu</h3>" +
                        "<p>Bạn vừa yêu cầu đặt lại mật khẩu.</p>" +
                        "<p>Click link bên dưới để tiếp tục:</p>" +
                        "<a href='" + resetLink + "'>Đặt lại mật khẩu</a>" +
                        "<br><br>" +
                        "<p>Link sẽ hết hạn sau 10 phút.</p>";

        sendHtmlEmail(toEmail, subject, content);
    }

    @Override
    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(MyConstants.MAIL_USERNAME);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

            logger.info("Send HTML mail success to {}", toEmail);

        } catch (MessagingException e) {
            logger.error("Send HTML mail failed", e);
            throw new RuntimeException("Không thể gửi email HTML");
        }
    }
}
