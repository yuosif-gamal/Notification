package com.example.Notification.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Map<String, EmailStrategy> emailStrategies;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(Map<String, Object> mailInfo) {
        String type = (String) mailInfo.get("type");
        EmailStrategy strategy = emailStrategies.get(type);

        if (strategy != null) {
            strategy.sendEmail(mailInfo);
        } else {
            LOGGER.error("No email strategy found for type: {}", type);
        }
    }
}
