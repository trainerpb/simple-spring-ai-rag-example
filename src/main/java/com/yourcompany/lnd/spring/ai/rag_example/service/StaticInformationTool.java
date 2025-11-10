package com.yourcompany.lnd.spring.ai.rag_example.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class StaticInformationTool {

    @Tool(description = "Use this period to Get the current date in YYYY-MM-DD format")
    public String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }

    @Tool(description = """
            Use this tool only to get the names of the founders of Ideal College of Engineering
            """)
    public String getNameOfFounders(){
        return """
                Mr. Parthasarathi Hesh and Dr. Raghupati Goswami. 
                """;
    }

    @Tool(description = """
            Use this tool only to get the name of the Principal of Ideal College of Engineering
            """)
    public String getPrincipalName(){
        return "Dr. Chiranjib Patra";
    }

    @Tool(description = """
            Use this tool to assume the name of the college in the context unless one is specified.
            """)
    public String  getThisCollege(){
        return "Ideal College of Engineering";
    }

    @Tool(description = """
            Use this tool only to get your own name. 
            Example: What's your name ?
            Example: Who are you ?
            Example: Who developed you? 
            Example: Who made you?
            """)
    public  String tellYourIdentity(){
        return """
                I'm Idealo! your AI-powered virtual assistant for Ideal College of Engineering.
                I'm here to help you with information about our college, courses, admissions, campus life, and more. Feel free to ask me anything related to Ideal College of Engineering!
                I'm an inhouse developed AI assistant designed to provide accurate and helpful information about Ideal College of Engineering.
                Prof. Soham Sengupta, Department of Computer Science and Engineering, envisaged me
              
               """;
    }
}
