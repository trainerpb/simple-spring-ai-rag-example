package com.yourcompany.lnd.spring.ai.rag_example.controller;

import com.yourcompany.lnd.spring.ai.rag_example.service.RagService;
import com.yourcompany.lnd.spring.ai.rag_example.service.pdf.PDFService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chrome")
@Slf4j
@RequiredArgsConstructor
public class ChromeApiController {
    private final PDFService pdfService;
    private final RagService ragService;

    @PostMapping("/chunks-to-vector-store")
    public Mono<String> toVectorStor(@RequestBody String bigText) throws TikaException, IOException {
        return Mono.fromCallable(() -> {
                    List<Document> documents = pdfService.saveChunks(bigText);
                    return "successfully added to vector store with size : " + documents.size();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(s -> s)
                ;


    }



    @GetMapping("/stream")
    public Flux<String> fetchStreaming(@RequestParam("q")String query){
        return ragService.retrieveChromePageAndGenerateStreaming(query);
    }

}
