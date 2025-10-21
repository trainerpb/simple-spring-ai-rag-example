package com.yourcompany.lnd.spring.ai.rag_example.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PDFService {
    private  final VectorStore vectorStore;

    public PDFService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> saveChunks(File file) throws TikaException, IOException {
        var chunks=loadChunkPdf(file);
        vectorStore.add(chunks);
        return chunks;
    }

    public List<Document> loadChunkPdf(File file) throws IOException, TikaException {
        String extractedText = this.extractText(new FileInputStream(file));
        List<Document> chunks = new ArrayList<>();
        int chunkSize = 500;
        for (int i = 0; i < extractedText.length(); i += chunkSize) {
            String chunk = extractedText.substring(i, Math.min(extractedText.length(), i + chunkSize));
            chunks.add(new Document(chunk));
        }
        return chunks;

    }


    public String extractText(InputStream pdfInputStream) throws TikaException, IOException {
        // Use Apache Tika or any other library to extract text from PDF
        Tika tika = new Tika();
        var text= tika.parseToString(pdfInputStream);
        log.info("Extracted Text: {}", text);
        return text;

    }

}
