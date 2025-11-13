package com.yourcompany.lnd.spring.ai.rag_example.model.entity.tool;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ToolResponse {
    private String answer;
    private List<String> tools;

}
