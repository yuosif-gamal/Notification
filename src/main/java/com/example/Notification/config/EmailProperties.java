package com.example.Notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
@Getter
@Setter
public class EmailProperties {

    private String subjectRegisterComplete;
    private String textRegisterComplete;
    private String unsubscribeServiceUrl;

    private String subjectItemNotReserved;
    private String textItemNotReserved;
}
