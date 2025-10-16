package com.soham.lnd.spring.ai.rag1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component

public class DocumentLoaderOnStartup implements CommandLineRunner {
    private final VectorStore vectorStore;

    public DocumentLoaderOnStartup(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    static List<Document> documentList = List.of(new Document("Soham got 940 in the exam"),
            new Document("SR got 970 in the exam"),
            new Document("BS got 965 in the exam"),
            new Document("PM got 947 in the exam"),
            new Document("SC got 938 in the exam"),
            new Document("AM got 935 in the exam"),
            new Document("Soham got 940 in the exam")

    );

    @Override
    public void run(String... args) throws Exception {
        vectorStore.add(documentList);
        System.out.println("DocumentLoaderOnStartup.run :: Added to the vector store : {}"+documentList.size());
    }
}
