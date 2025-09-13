package com.soumenprogramming.byte2byte.learn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendRegistrationSuccessEmail(String toEmail, String username, String fullName,String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Welcome to Byte2ByteLearn - Registration Successful!");
            
            String emailBody = buildRegistrationEmailBody(username, fullName,password);
            message.setText(emailBody);
            
            mailSender.send(message);
            LOG.info("Registration success email sent to: {}", toEmail);
            
        } catch (Exception e) {
            LOG.error("Failed to send registration email to {}: {}", toEmail, e.getMessage());
            // Don't throw exception to avoid breaking registration flow
        }
    }

    private String buildRegistrationEmailBody(String username, String fullName, String password) {
        String template = """
        Dear %s,

        Welcome to Byte2ByteLearn!

        Your registration has been completed successfully. Here are your account details:

        Username: %s
        Full Name: %s
        Password:   %s

        You can now log in to your account and start exploring our courses and learning materials.

        If you have any questions or need assistance, please don't hesitate to contact our support team.

        Happy Learning!

        Best regards,
        The Byte2ByteLearn Team
        """;
        return String.format(
                template,
                fullName != null ? fullName : username,
                username,
                fullName != null ? fullName : "Not provided",
                password != null ? password : "Not provided"
        );
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            LOG.info("Email sent successfully to: {}", toEmail);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
