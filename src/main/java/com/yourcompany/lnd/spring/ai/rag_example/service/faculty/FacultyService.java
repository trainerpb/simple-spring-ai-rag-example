package com.yourcompany.lnd.spring.ai.rag_example.service.faculty;

import com.yourcompany.lnd.spring.ai.rag_example.service.faculty.model.FacultyInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyService {

    private  final JdbcTemplate jdbcTemplate;
    private final VectorStore vectorStore;


    public List<FacultyInfo> getAllFacultyInfo(){
        String sql = "SELECT sl_no, name_of_the_faculty, designation, dept, contact_no, email_id FROM faculty";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new FacultyInfo(
                rs.getInt("sl_no"),
                rs.getString("name_of_the_faculty"),
                rs.getString("designation"),
                rs.getString("dept"),
                rs.getString("contact_no"),
                rs.getString("email_id")
        ));
    }

    public List<Document> chunkFacultyInfo(){
        List<FacultyInfo> facultyInfos = getAllFacultyInfo();
        List<Document> documents = facultyInfos.stream()
                .map(facultyInfo -> new Document(facultyInfo.toString()))
                .toList();
        vectorStore.add(documents);
        log.info("Faculty information has been chunked and added to the vector store. Total documents: {}", documents.size());
        return documents;
    }




}
