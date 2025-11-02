package home.soham.tools.docs.ai.repository;

import home.soham.tools.docs.ai.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, String> {

    public List<Insurance> findByInsuredContainingIgnoreCase(String insured);

    @Query("SELECT i FROM Insurance i WHERE MONTH(TO_DATE(i.premium_date,'YYYY-MM-DD')) = :month")
    List<Insurance> listByPremiumMonth(@Param("month") int month);

}
