package com.yourcompany.lnd.spring.ai.rag_example.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ATestTool {

    @Tool(description = "Open notepad application locally")
    public String simpleTool() {
        log.info("simpleTool :: Processed at: {}" , LocalDateTime.now());
        return "simpleTool :: Processed: " + LocalDateTime.now();
    }
}
