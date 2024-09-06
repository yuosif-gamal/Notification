package com.example.Notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Notification.service.EmailService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody Map<String, Object> emailInfo) throws IOException {
        emailService.sendEmail(emailInfo);
    }
}
