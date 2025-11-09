package com.yourcompany.lnd.spring.ai.rag_example.service.tool;

import com.yourcompany.lnd.spring.ai.rag_example.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsiteLinkTool {

    private final AppConfig appConfig;

    @Tool(description = """
            Use this tool to get the official website link for a given stream of study.
            Provide the link only, without any additional text. if link is empty or blank, return "No link found. Please refer to the website".
            Example streams: Computer Science, Electrical Engineering, Mechanical Engineering, Civil Engineering, Chemical Engineering, Aerospace Engineering, Biomedical Engineering.
            Computer Science is also known as CSE or CS.
            Electrical Engineering is also known as EEE or EE.
            Mechanical Engineering is also known as ME.
            Civil Engineering is also known as CE.
            BBA is also known as Bachelor of Business Administration.
            Artificial Intelligence is also known as AI.
            Machine Learning is also known as ML.
            Artificial Intelligence and Machine Learning is also known as AI & ML or AIML
            """, returnDirect = true)
    public String getWebsiteLink(@ToolParam(description = """
            Provide the stream of study for which you want to get the official website link.
            Examples: Computer Science, Electrical Engineering, Mechanical Engineering, Civil Engineering, Chemical Engineering, Aerospace Engineering, Biomedical Engineering, BBA, Artificial Intelligence, Machine Learning, AI & ML.
            Computer Science is also known as CSE or CS.
            Electrical Engineering is also known as EEE or EE.
            Mechanical Engineering is also known as ME.
            Civil Engineering is also known as CE.
            BBA is also known as Bachelor of Business Administration.
            Artificial Intelligence is also known as AI.
            Machine Learning is also known as ML.
            Artificial Intelligence and Machine Learning is also known as AI & ML or AIML
            
            """) String stream) {
        String link = appConfig.getWebsiteLinks().getOrDefault(normalizeStreamName(stream), Strings.EMPTY);
        if (!StringUtils.hasText(link)) {
            log.warn("WebsiteLinkTool.getWebsiteLink :: No link found for stream: {}", stream);

        } else {
            log.info("WebsiteLinkTool.getWebsiteLink :: Found link for stream: {} -> {}", stream, link);
        }
        return link;
    }


    @Tool(description = """
            Use this tool to open the official website link page for a given sections of study in a new tab.
            Example command: visit page for CSE / Computer Science/ CS, EEE / Electrical Engineering / EE, ME / Mechanical Engineering, CE / Civil Engineering, BBA, AI / Artificial Intelligence, ML / Machine Learning, AI & ML.
            Provide the link only, without any additional text. if link is empty or blank, return "No link found. Please refer to the website".
            Example streams: Computer Science, Electrical Engineering, Mechanical Engineering, Civil Engineering, Chemical Engineering, Aerospace Engineering, Biomedical Engineering.
            Computer Science is also known as CSE or CS.
            Electrical Engineering is also known as EEE or EE.
            Mechanical Engineering is also known as ME.
            Civil Engineering is also known as CE.
            BBA is also known as Bachelor of Business Administration.
            Artificial Intelligence is also known as AI.
            Machine Learning is also known as ML.
            Artificial Intelligence and Machine Learning is also known as AI & ML or AIML
            """, returnDirect = true)
    public String visitWebsiteLink(@ToolParam(description = """
            Provide the stream of study for which you want to get the official website link.
            Examples: Computer Science, Electrical Engineering, Mechanical Engineering, Civil Engineering, Chemical Engineering, Aerospace Engineering, Biomedical Engineering, BBA, Artificial Intelligence, Machine Learning, AI & ML.
            Computer Science is also known as CSE or CS.
            Electrical Engineering is also known as EEE or EE.
            Mechanical Engineering is also known as ME.
            Civil Engineering is also known as CE.
            BBA is also known as Bachelor of Business Administration.
            Artificial Intelligence is also known as AI.
            Machine Learning is also known as ML.
            Artificial Intelligence and Machine Learning is also known as AI & ML or AIML
            
            """) String stream) {
        String link = appConfig.getWebsiteLinks().getOrDefault(normalizeStreamName(stream), Strings.EMPTY);
        return """
                <button onclick="window.open('%s', '_blank')">
                    Visit the official website for %s </button>
                """.formatted(link, stream);
    }


    protected String normalizeStreamName(String stream) {
        String normalizedStream = stream.trim().toLowerCase();
        switch (normalizedStream) {
            case "cse", "computer science", "cs" -> normalizedStream = "cse";
            case "eee", "electrical engineering", "ee", "electrical" -> normalizedStream = "ee";
            case "me", "mechanical engineering" -> normalizedStream = "me";
            case "ce", "civil engineering" -> normalizedStream = "ce";
            case "bba", "bachelor of business administration" -> normalizedStream = "bba";
            case "ai", "artificial intelligence" -> normalizedStream = "ai";
            case "ml", "machine learning" -> normalizedStream = "ai";
            case "aiml", "ai & ml", "artificial intelligence and machine learning" -> normalizedStream = "ai";
            default -> normalizedStream = "Link is not found. Please refer to the website.";
        }
        return normalizedStream;
    }

}
