package com.yourcompany.lnd.spring.ai.rag_example.service.faculty;

import com.yourcompany.lnd.spring.ai.rag_example.service.faculty.model.FacultyInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FacultyServiceTest {

    @Autowired
    private FacultyService facultyService;

    @Test
    void getAllFacultyInfo() {
        List<FacultyInfo> allFacultyInfo = facultyService.getAllFacultyInfo();
        log.info("Retrieved Faculty Info: {}", allFacultyInfo);
        assertNotNull(allFacultyInfo, "The returned faculty info list should not be null");
    }

    @Test
    public void chunkFacultyInfo() {
       facultyService.chunkFacultyInfo().forEach(d->{
              log.info("Document added: {} metaData:{}",d.getText(),d.getMetadata());

       });
    }
}