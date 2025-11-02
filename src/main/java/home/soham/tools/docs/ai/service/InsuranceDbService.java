package home.soham.tools.docs.ai.service;

import home.soham.tools.docs.ai.model.Insurance;
import home.soham.tools.docs.ai.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class InsuranceDbService {
    private final InsuranceRepository insuranceRepository;

    public List<Insurance> findAll(){
        return insuranceRepository.findAll();
    }

    public Optional<Insurance> findByPolicyNumber(String policyNumber){
        return insuranceRepository.findById(policyNumber);
    }

    public List<Insurance> findByPolicyNumbers(Set<String> policyNumbers){
        return insuranceRepository.findAllById(policyNumbers);
    }

    public  List<Insurance> findByInsured(String insured){
        return insuranceRepository.findByInsuredContainingIgnoreCase(insured);
    }

    public List<Insurance> findByInsuranceMonth(Month month){
        return  insuranceRepository.listByPremiumMonth(month.getValue());
    }

}
