package com.soham.lnd.spring.ai.rag1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebMvcController {
    @GetMapping("/home")
    public String home(){
        return "ChatClient";
    }

    @GetMapping("/qr-scanner")
    public String qrScanner(){
        return "QR-Scanner";
    }
}
