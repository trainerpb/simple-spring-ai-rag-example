package com.soham.lnd.spring.ai.rag1.controller;


import com.soham.lnd.spring.ai.rag1.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search")

public class SeasrchApiController {
    private final RagService ragService;

    public SeasrchApiController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/blocking")
    public String fetchBlock(@RequestParam("q")String query){
        return ragService.retrieveAndGenerate(query);
    }



    @GetMapping("/stream")
    public Flux<String> fetchStreaming(@RequestParam("q")String query){
        return ragService.retrieveAndGenerateStreming(query);
    }
}
