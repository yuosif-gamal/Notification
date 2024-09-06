package com.example.Notification.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private String htmlContent;

    @Value("${email.unsubscribeServiceUrl}")
    String ApiGatewayURL;
    public void sendEmail(Map<String, Object> mailInfo) throws IOException {
        mailInfo.put("unsubscribeLink" , ApiGatewayURL);
        String templateName = mailInfo.get("type") + ".html";

        String templateDir = "D:\\spring boot\\Notification\\src\\main\\resources\\templates";

        File fileResource = new File(templateDir, templateName);

        validateExistingType(fileResource);
        htmlContent = getHtmlContent(fileResource);
        htmlContent = prepareContent(mailInfo);


        sendEmailWithHtml(mailInfo.get("email").toString(), mailInfo.get("type").toString(), htmlContent);
    }

    private void validateExistingType(File fileResource) throws IOException {
        if (!fileResource.exists()) {
            throw new FileNotFoundException("Email Template file not found: " + fileResource.getAbsolutePath());
        }
    }
    private String getHtmlContent(File fileResource) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileResource)) {
           return htmlContent = StreamUtils.copyToString(fileInputStream, StandardCharsets.UTF_8);
        }
    }

    private String prepareContent(Map<String, Object> mailInfo) {
        for (Map.Entry<String, Object> entry : mailInfo.entrySet()) {
            String placeholder = "\\[\\[" + entry.getKey() + "\\]\\]";
            htmlContent = htmlContent.replaceAll(placeholder, String.valueOf(entry.getValue()));
        }
        return htmlContent;
    }

    private void sendEmailWithHtml(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
