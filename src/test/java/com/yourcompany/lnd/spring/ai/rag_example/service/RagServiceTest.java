package com.yourcompany.lnd.spring.ai.rag_example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class RagServiceTest {
    @Autowired
    RagService ragService;

    public static List<String> staticInfoToolTestPrompt() {
        return List.of(
                "who founded ideal college of engineering and when?",
                "what are the courses offered in ideal college of engineering?",
                "where is ideal college of engineering located?",
                "who is the principal of ideal college of engineering?"

        );
    }


    @ParameterizedTest
    @MethodSource("staticInfoToolTestPrompt")
    void retrieveAndGenerate(String input) {
       var a= ragService.retrieveAndGenerateStreaming(input);
       log.info("RagServiceTest.retrieveAndGenerate: {}",a.collectList().block());
    }


}