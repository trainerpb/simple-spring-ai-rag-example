package com.yourcompany.lnd.spring.ai.rag_example.controller;


import com.yourcompany.lnd.spring.ai.rag_example.service.RagService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search")

public class RagApiController {
    private final RagService ragService;

    public RagApiController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/stream/{version}")
    public Flux<String> fetchStreaming(@RequestParam("q")String query, @PathVariable(name = "version",required = true) String version) {

        return switch (version){
            case "v1" -> ragService.retrieveAndGenerateStreaming(query);
            case "v2" -> ragService.retrieveAndGenerateStreaming_V2(query);
            default -> throw new IllegalArgumentException("Unsupported version: " + version);
        };
    }
}
