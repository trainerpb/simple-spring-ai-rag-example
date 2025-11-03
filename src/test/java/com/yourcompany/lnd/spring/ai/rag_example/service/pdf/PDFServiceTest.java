package com.yourcompany.lnd.spring.ai.rag_example.service.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PDFServiceTest {

    private PDFService pdfService= new PDFService(null);

    @Test
    void extractParagraphs() throws IOException {
        List<String> paragraphs = pdfService.extractParagraphs("C:\\Works\\ebooks\\16_EBOOK-7th_ed_software_engineering_a_practitioners_approach_by_roger_s._pressman_.pdf");
        System.out.println(paragraphs.size());
        paragraphs.forEach(System.out::println);

    }
}