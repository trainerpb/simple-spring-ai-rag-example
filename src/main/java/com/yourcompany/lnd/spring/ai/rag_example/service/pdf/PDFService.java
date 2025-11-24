package com.yourcompany.lnd.spring.ai.rag_example.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class PDFService {
    private  final VectorStore vectorStore;

    public PDFService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> saveChunks(File file) throws TikaException, IOException {
        var chunks=loadChunkPdf(file);
//        var chunks=loadPargaraphwiseStripping(file);
        log.info("Obtained chunks size : {}",chunks.size());
        int batch_size=10;
        int batch_count = (int) Math.ceil((double) chunks.size() / batch_size);
        for (int i = 0; i < batch_count; i++) {
           int start= i * batch_size;
           int end = Math.min(start + batch_size, chunks.size());
              List<Document> batch = chunks.subList(start, end);
                vectorStore.add(batch);
                log.info("Added batch {} to vector store with size : {}",i+1,batch.size());

        }

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

    public List<Document> loadPargaraphwiseStripping(File file) throws IOException, TikaException {

        List<Document> chunks = new ArrayList<>();
        extractParagraphs(file.getPath()).forEach(paragraph->{

            int chunkSize = 500;
            for (int i = 0; i < paragraph.length(); i += chunkSize) {
                String chunk = paragraph.substring(i, Math.min(paragraph.length(), i + chunkSize));
                chunks.add(new Document(chunk));
               log.info("PDFService.loadPargaraphwiseStripping :: Added chunk of size : {}",chunk.length());
            }
            chunks.add(new Document(paragraph));
        });
        return chunks;

    }


    public String extractText(InputStream pdfInputStream) throws TikaException, IOException {
        // Use Apache Tika or any other library to extract text from PDF
        Tika tika = new Tika();
        var text= tika.parseToString(pdfInputStream);
        PDFTextStripper pdfTextStripper=new PDFTextStripper();

        log.info("Extracted Text: {}", text);
        return text;

    }

    public  List<String> extractParagraphs(String filePath) throws IOException {

        List<String> paragraphs = new ArrayList<>();
        try(PDDocument document = PDDocument.load(new File(filePath))) {

            int numberOfPages = document.getNumberOfPages();
           for(int page=0;page<numberOfPages;page++){
               PDFTextStripper pdfTextStripper=new PDFTextStripper();
               pdfTextStripper.setStartPage(page+1);
               pdfTextStripper.setEndPage(page+1);
               String pageText = pdfTextStripper.getText(document);
               String[] pageParagraphs = pageText.split("\\r?\\n\\r?\\n");
               for (String paragraph : pageParagraphs) {
                   paragraphs.add(paragraph.trim().replaceAll("\\x00", ""));
               }
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return paragraphs;
    }




    public void renameFilesWithinFolderToAnoterDirectory(File directory,String targetDir) throws TikaException, IOException {
        int count=0;
        File[] files = directory.listFiles();
        for (File fileEntry:files){
            if(fileEntry.exists() && fileEntry.isFile()){
                Tika tika = new Tika();
                String content = tika.parseToString(fileEntry);
                //The Guardians of 27900124001 SANTOSH KUMAR RAJAK
                final String PATTERN="The Guardians  of  ";
                int offset=content.indexOf(PATTERN) +PATTERN.length();
                String regNo=content.substring(offset,offset+11);

                    String newFileName = regNo +".pdf";
                File tagetFile=new File(targetDir , newFileName);
                Files.copy(fileEntry.toPath(),tagetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    log.info("Copied new file name: {} for file: {}",tagetFile,fileEntry.getName());
                count++;
            }else{
                log.warn("Skipping renaming for non file entry : {}",fileEntry.getName());
            }

            log.info("Total files processed for renaming : {}",count);
        }
    }

}
