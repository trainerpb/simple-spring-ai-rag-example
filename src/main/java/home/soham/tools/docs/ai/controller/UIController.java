package home.soham.tools.docs.ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping("/home")
    public String home(){
        return "ChatClient";
    }
}
