package com.yourcompany.lnd.spring.ai.rag_example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMvcController {
    @GetMapping("/home")
    public String home(){
        return "ChatClient";
    }
}
