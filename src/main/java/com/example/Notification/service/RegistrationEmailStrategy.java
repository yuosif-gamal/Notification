package com.example.Notification.service;

import com.example.Notification.config.EmailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service("registration")
public class RegistrationEmailStrategy implements EmailStrategy {

    private final JavaMailSender emailSender;
    private final EmailProperties emailProperties;

    @Override
    public void sendEmail(Map<String, Object> mailInfo) {
        String to = (String) mailInfo.get("email");
        String username = (String) mailInfo.get("username");
        String unsubscribeLink = emailProperties.getUnsubscribeServiceUrl() + "users/subscribe-status/" + mailInfo.get("id");

        String text = String.format(emailProperties.getTextRegisterComplete(), to, username, unsubscribeLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(emailProperties.getSubjectRegisterComplete());
        message.setText(text);

        emailSender.send(message);
    }
}
