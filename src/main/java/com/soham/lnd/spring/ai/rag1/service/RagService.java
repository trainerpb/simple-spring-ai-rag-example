package com.soham.lnd.spring.ai.rag1.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public String retrieveAndGenerate(String msg){
        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).build();
        List<Document> similaritySearchDocuments = vectorStore.similaritySearch(searchRequest);
        String informationAsString = similaritySearchDocuments.stream().map(Document::getText).collect(Collectors.joining("\n"));

        return null;
    }
}
