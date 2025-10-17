package com.soham.lnd.spring.ai.rag1.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompt/rag-prompt.st")
    private Resource promptResource;

    public RagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public String retrieveAndGenerate(String msg){
        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).build();
        List<Document> similaritySearchDocuments = vectorStore.similaritySearch(searchRequest);
        String informationAsString = similaritySearchDocuments.stream().map(Document::getText).collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptResource);
       var prompt= new Prompt(List.of(systemPromptTemplate.createMessage(Map.of("information",informationAsString)),
               new UserMessage(msg)
               ));
        var output= chatClient.prompt(prompt).call().content();
        return output;
    }


    public Flux<String> retrieveAndGenerateStreming(String msg){
        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).build();

        return Mono.fromCallable(()->{
                    List<Document> similaritySearchDocuments = vectorStore.similaritySearch(searchRequest);
                    String informationAsString = similaritySearchDocuments.stream().map(Document::getText).collect(Collectors.joining("\n"));
                    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptResource);
                    var prompt= new Prompt(List.of(systemPromptTemplate.createMessage(Map.of("information",informationAsString)),
                            new UserMessage(msg)
                    ));
                    var output= chatClient.prompt(prompt).stream().content();
                    return output;

        })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(flux -> flux);



    }
}
