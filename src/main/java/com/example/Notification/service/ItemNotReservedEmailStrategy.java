package com.example.Notification.service;

import com.example.Notification.config.EmailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service("NOT_RESERVED")
public class ItemNotReservedEmailStrategy implements EmailStrategy {

    private final JavaMailSender emailSender;
    private final EmailProperties emailProperties;

    @Override
    public void sendEmail(Map<String, Object> mailInfo) {
        String to = (String) mailInfo.get("email");
        String username = (String) mailInfo.get("username");
        String itemName = (String) mailInfo.get("itemName");

        String text = String.format(emailProperties.getTextItemNotReserved(), username, itemName);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(emailProperties.getSubjectItemNotReserved());
        message.setText(text);

        emailSender.send(message);
    }
}
