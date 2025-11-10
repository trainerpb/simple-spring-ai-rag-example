package com.yourcompany.lnd.spring.ai.rag_example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "iee.website.app")
@Data
public class AppConfig
{
    private Map<String, String> departmentWebsiteLinks;


}
