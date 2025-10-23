package com.yourcompany.lnd.spring.ai.rag_example.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
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

    public Flux<String> retrieveAndGenerateStreaming(String msg) {

        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).build();

        return Mono.fromCallable(() -> {
                    List<Document> similaritySearchDocuments = vectorStore.similaritySearch(searchRequest);
                    String informationAsString = similaritySearchDocuments.stream().map(Document::getText).collect(Collectors.joining("\n"));
                    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptResource);
                    var prompt = new Prompt(List.of(systemPromptTemplate.createMessage(Map.of("information", informationAsString)),
                            new UserMessage(msg)
                    ));
                    return chatClient.prompt(prompt).stream().content();

                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(flux -> flux);


    }


    /**
     * Improved version with better readability using Advisor in isolation
     *
     *
     * @param msg
     * @return
     */
    public Flux<String> retrieveAndGenerateStreaming_V2(String msg) {

        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).
                similarityThreshold(0.6).
                build();

        return Mono.fromCallable(() -> {


                    QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore).
                            searchRequest(searchRequest)  // Use this to pass topK and other params , else default will be used
                            .build();

                    return chatClient.prompt(msg)
                            .advisors(new SimpleLoggerAdvisor(),questionAnswerAdvisor)
                            .stream().content();

                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(flux -> flux);


    }


}
