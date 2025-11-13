package com.yourcompany.lnd.spring.ai.rag_example.service;


import com.yourcompany.lnd.spring.ai.rag_example.service.tool.StaticInformationTool;
import com.yourcompany.lnd.spring.ai.rag_example.service.tool.WebsiteLinkTool;
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
    private final WebsiteLinkTool websiteLinkTool;
    private final StaticInformationTool staticInformationTool;
    @Value("classpath:/prompt/rag-prompt.st")
    private Resource promptResource;

    public RagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, WebsiteLinkTool websiteLinkTool, StaticInformationTool staticInformationTool) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
        this.websiteLinkTool = websiteLinkTool;
        this.staticInformationTool = staticInformationTool;
    }

    public Flux<String> retrieveAndGenerateStreaming(String msg){



        return Mono.fromCallable(()->{
                    var toolCallingPrompt= new Prompt(new SystemMessage("""
                You are a helpful assistant that helps employees with information about the organization.
                You should query available tools to get accurate information to answer user queries.
                Do not make up answers.
                
                Output in json  format.
                Example output:
                {
                  "answer": "The answer to the user's question.",
                  "tools": ["tool1", "tool2"]
                }
                
                If you are unsure, just return: 
                {
                    "answer": null,
                "tools": []
                }
                """),

                            new UserMessage(msg)
                    );
                    var toolResponse = chatClient.prompt(toolCallingPrompt)
                            .tools(staticInformationTool, websiteLinkTool)
                            .call().content();
                    log.info("Tool response: {}", toolResponse);

                    SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3).build();

                    List<Document> similaritySearchDocuments = vectorStore.similaritySearch(searchRequest);
                    String informationAsString = similaritySearchDocuments.stream().map(Document::getText).collect(Collectors.joining("\n"));
                    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptResource);
                    var prompt= new Prompt(List.of(systemPromptTemplate.createMessage(Map.of("information",informationAsString)),
                            new UserMessage(msg)
                    ));
                    return chatClient.prompt(prompt)
                            .tools(staticInformationTool, websiteLinkTool)
                            .stream().content();

        })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(flux -> flux);



    }



}
