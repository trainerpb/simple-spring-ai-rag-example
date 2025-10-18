package com.soham.lnd.spring.ai.rag1.service.pdf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConditionalOnProperty("app.vector.load-on-start-up")
public class PdfChunkLoaderOnStartup implements CommandLineRunner {

    private final String filePath;

    private final PdfChukerService pdfChukerService;

    public PdfChunkLoaderOnStartup(@Value("${app.vector.pdf.file-path}") String filePath, PdfChukerService pdfChukerService) {
        this.filePath = filePath;
        this.pdfChukerService = pdfChukerService;
    }

    @Override
    public void run(String... args) throws Exception {
       var documentList= pdfChukerService.saveChunks(new File(filePath));
        System.out.println("PdfChunkLoaderOnStartup.run :: Added to the vector store : {}"+documentList.size());
    }
}
