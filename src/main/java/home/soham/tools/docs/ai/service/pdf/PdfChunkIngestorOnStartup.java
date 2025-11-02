package home.soham.tools.docs.ai.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConditionalOnProperty("app.vector.load-on-start-up")
@Slf4j
public class PdfChunkIngestorOnStartup implements CommandLineRunner {

    private final String filePath;

    private final PDFService pdfChukerService;

    public PdfChunkIngestorOnStartup(@Value("${app.vector.pdf.file-path}") String filePath, PDFService pdfChukerService) {
        this.filePath = filePath;
        this.pdfChukerService = pdfChukerService;
    }

    @Override
    public void run(String... args) throws Exception {
       var documentList= pdfChukerService.saveChunks(new File(filePath));
       log.info("PdfChunkLoaderOnStartup.run :: Added to the vector store : {}",documentList.size());
    }
}
