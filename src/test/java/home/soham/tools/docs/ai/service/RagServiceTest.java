package home.soham.tools.docs.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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


    @Test
    void queryDatabaseAndAnswerInsuranceDetails() {

            var a = ragService.queryDatabaseAndAnswerInsuranceDetails("list all insurance docs");
             var responseList = a.collectList().block();
            log.info("RagServiceTest.queryDatabaseAndAnswerInsuranceDetails: {}", responseList);

    }
}