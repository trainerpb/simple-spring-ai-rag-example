package com.yourcompany.lnd.spring.ai.rag_example.service;

import com.yourcompany.lnd.spring.ai.rag_example.config.AppConfig;
import com.yourcompany.lnd.spring.ai.rag_example.model.entity.StaticInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaticInformationTool {

    private final AppConfig appConfig;

    @Tool(description = "Use this period to Get the current date in YYYY-MM-DD format")
    public String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }


    @Tool(description = """
            Use this tool to assume the name of the college in the context unless one is specified.
            """, returnDirect = true)
    public String getThisCollege() {
        return "Ideal College of Engineering";
    }

    @Tool(description = """
            Use this tool only to get your own name. 
            Example: What's your name ?
            Example: Who are you ?
            Example: Who developed you? 
            Example: Who made you?
            """, returnDirect = true)
    public String tellYourIdentity() {
        return """
                I'm Idealo! Prof. Soham Sengupta, Department of Computer Science and Engineering, envisaged me
                 your AI-powered virtual assistant for Ideal College of Engineering.
                 I'm here to help you with information about our college, courses, admissions, campus life, and more. Feel free to ask me anything related to Ideal College of Engineering!
                 I'm an inhouse developed AI assistant designed to provide accurate and helpful information about Ideal College of Engineering.
                """;
    }

    @Tool(description = """
            Always Use this tool to get static information about the college based on the keys provided. The first level key and second level key will help you to get the information from the static information map.
            What is the name of the founders means keyA = "name", keyB = "founders"
            Who are the founders of the college means keyA = "name", keyB = "founders"
            Tell me about the intake of ECE department means keyA = "intake", keyB =
            Example: What is the name of founders?
            Example: Who are the founders of the college?
            Example: Tell me about the intake of ECE department.
            Example: keyA = "name", keyB = "founders"
            Example: keyA = "intake", keyB = "ECE" (stream)
            Example: keyA = "name", keyB = "Principal" (person or entity)
            Example: keyA = "name", keyB = "courses"
            Example: keyA = "name", keyB = "founder"
            Example: How many students capacity in CSE department means keyA = "intake", keyB = "CSE"
            
            """, returnDirect = true)
    public String getStaticInformation(@ToolParam(description = """
                                               First level key to access the static information map.
                                               Example: "name", "intake",  "contact"
                                               """) String keyA,
                                       @ToolParam(description = """
                                               Second level key to access the static information map.
                                               Example: "founders", "ECE", "Principal", "courses"
                                               Example: If keyA is "name", keyB can be "founders" or "Principal" or "courses"
                                               Example: If keyA is "intake", keyB can be "CSE", "ECE", "ME" etc.
                                               """)
                                       String keyB) {

        return StaticInfoRequest.findByType(appConfig.getInfo(), keyA,keyB).toString() ;

    }
}
