package com.example.Notification.service;

import java.util.Map;

public interface EmailStrategy {
    void sendEmail(Map<String, Object> mailInfo);
}
