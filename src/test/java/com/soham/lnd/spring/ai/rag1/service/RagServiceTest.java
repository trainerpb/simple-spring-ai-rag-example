package com.soham.lnd.spring.ai.rag1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RagServiceTest {
    @Autowired
    RagService ragService;


    @Test
    void retrieveAndGenerate() {
       var a= ragService.retrieveAndGenerate("who scored exactly 970?");
        System.out.println("RagServiceTest.retrieveAndGenerate: "+a);
    }

    @Test
    void retrieveAndGenerateStreming() {
        var a= ragService.retrieveAndGenerate("who scored exactly 970?");
        System.out.println("RagServiceTest.retrieveAndGenerate: "+a);
    }
}