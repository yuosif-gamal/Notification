package com.example.Notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Notification.service.EmailService;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email")
    public void sendEmail(@RequestParam String to,
                          @RequestParam String userName,
                          @RequestParam String itemName) {
        emailService.sendItemNotReservedEmail(to, userName, itemName);
    }
}
