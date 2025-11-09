package com.yourcompany.lnd.spring.ai.rag_example.controller;


import com.yourcompany.lnd.spring.ai.rag_example.service.RagService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class RagApiController {
    private final RagService ragService;

    public RagApiController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/stream")
    public Flux<String> fetchStreaming(@RequestParam("q")String query){
        return ragService.retrieveAndGenerateStreaming(query);
    }
}
