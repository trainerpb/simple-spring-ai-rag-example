package com.soham.lnd.spring.ai.rag1;

import com.soham.lnd.spring.ai.rag1.service.RagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleSpringAiRagExampleApplicationTests {
@Autowired
	RagService ragService;
	@Test
	void contextLoads() {
		ragService.retrieveAndGenerate("who scored 940?");
	}



}
