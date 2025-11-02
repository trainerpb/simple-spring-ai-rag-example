package home.soham.tools.docs.ai.repository;

import home.soham.tools.docs.ai.model.Insurance;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InsuranceRepositoryTest {

    @Autowired
    private InsuranceRepository insuranceRepository;


    @Test
    public void saveTest(){
        Insurance insurance = Insurance.builder()
                .policy_no("TEST123")
                .insured("Soham")
                .company("LIC")
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 1))
                .sa(1000000)
                .premium_date("2023-01-01")
                .premium_amount(12000)
                .contact("9876543210")
                .build();

        insuranceRepository.save(insurance);

        Optional<Insurance> found = insuranceRepository.findById("TEST123");
        assertTrue(found.isPresent());
        assertEquals("Soham", found.get().getInsured());

        List<Insurance> insurancesForSoham = insuranceRepository.findByInsuredContainingIgnoreCase("soham");
        assertTrue(CollectionUtils.isNotEmpty(insurancesForSoham));

        List<Insurance> insurancesForUnknown
                = insuranceRepository.findByInsuredContainingIgnoreCase("unknown");
        assertTrue(CollectionUtils.isEmpty(insurancesForUnknown));

        List<Insurance> insurancesRenewalDueInJan = insuranceRepository.listByPremiumMonth(Month.JANUARY.getValue());
        assertEquals(3, insurancesRenewalDueInJan.size());

    }


}