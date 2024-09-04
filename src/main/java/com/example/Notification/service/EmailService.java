package com.example.Notification.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender emailSender;
    private final RestTemplate restTemplate;

    @Value("${email.subject.itemNotReserved}")
    private String itemNotReservedSubject;

    @Value("${email.text.itemNotReserved}")
    private String itemNotReservedText;

    @Value("${email.subject.registerComplete}")
    private String registerCompleteSubject;

    @Value("${email.text.registerComplete}")
    private String registerCompleteText;

    @Value("${unsubscribe.service.url}")
    private String unsubscribeServiceUrl;

    @Async
    public void sendItemNotReservedEmail(String to, String userName, String itemName, Long userId) {
        LOGGER.info("Preparing to send email. Recipient: {}, Subject: {}, UserName: {}, ItemName: {}",
                to, itemNotReservedSubject, userName, itemName);

        String text = createEmailText(itemNotReservedText, userName, itemName, userId);
        sendEmail(to, itemNotReservedSubject, text);
    }

    @Async
    public void sendRegistrationCompleteEmail(String to, String userName, Long userId) {
        String text = createEmailText(registerCompleteText, to, userName, userId);
        LOGGER.info("Preparing text to send: {} ", text);

        sendEmail(to, registerCompleteSubject, text);
    }

    private String createEmailText(String template, String userName, String itemName, Long userId) {
        String unsubscribeLink = unsubscribeServiceUrl + "users/subscribe-status/" + userId;
        return String.format(template, userName, itemName, unsubscribeLink);
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
            LOGGER.error("Failed to send email to {}. Subject: {}. Exception: {}", to, subject, e.getMessage(), e);
        }
    }
}
