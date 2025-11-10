package com.yourcompany.lnd.spring.ai.rag_example.config;

import com.yourcompany.lnd.spring.ai.rag_example.model.entity.StaticInfoRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "iee.website.app")
@Data
public class AppConfig
{
    private Map<String, String> departmentWebsiteLinks;

    private Map<String, Map<String, Set<String>>> info = new HashMap<>();
}
