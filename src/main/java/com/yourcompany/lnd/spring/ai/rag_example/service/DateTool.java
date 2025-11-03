package com.yourcompany.lnd.spring.ai.rag_example.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class DateTool {

    @Tool(description = "Use this period to Get the current date in YYYY-MM-DD format")
    public String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}
