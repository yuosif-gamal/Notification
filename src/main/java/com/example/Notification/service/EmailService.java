package com.example.Notification.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender emailSender;

    @Value("${email.subject.itemNotReserved}")
    private String itemNotReservedSubject;

    @Value("${email.text.itemNotReserved}")
    private String itemNotReservedText;

    public void sendItemNotReservedEmail(String to, String userName, String itemName) {
        LOGGER.info("Preparing to send email. Recipient: {}, Subject: {}, UserName: {}, ItemName: {}",
                to, itemNotReservedSubject, userName, itemName);

        String text = String.format(itemNotReservedText, userName, itemName);
        LOGGER.debug("Email body: {}", text);

        sendEmail(to, itemNotReservedSubject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            LOGGER.info("Sending email to {} with subject {}", to, subject);
            emailSender.send(message);
            LOGGER.info("Email sent to {}", to);
        } catch (Exception e) {
            LOGGER.error("Failed to send email to {}. Subject: {}. Exception: {}", to, subject, e.getMessage());
        }
    }
}
