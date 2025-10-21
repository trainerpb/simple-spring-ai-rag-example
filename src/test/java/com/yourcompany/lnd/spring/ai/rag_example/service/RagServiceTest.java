package com.yourcompany.lnd.spring.ai.rag_example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RagServiceTest {
    @Autowired
    RagService ragService;


    @Test
    void retrieveAndGenerate() {
       var a= ragService.retrieveAndGenerateStreaming("the lion and the ass summary");
       log.info("RagServiceTest.retrieveAndGenerate: {}",a);
    }


}