package home.soham.tools.docs.ai.service.tool;

import home.soham.tools.docs.ai.service.InsuranceDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component

@Slf4j
@RequiredArgsConstructor
public class InsuranceTool {
    
    private  final InsuranceDbService insuranceDbService;


    @Tool(description = """
            
            Always use this tool to  fetch list of all policy or insurance details or documents
            
            """,returnDirect = true)
    public String findAll() {
        log.info("findAll():: Tool got invoked");
        return toString(insuranceDbService.findAll().stream(), Object::toString);
    }

//    @Tool(name ="findByPolicyNumber" )
    public String findByPolicyNumber(String policyNumber){
        var policyOpt= insuranceDbService.findByPolicyNumber(policyNumber);
        return  policyOpt.isPresent()? policyOpt.get().toString() : "Insurance number : "+policyNumber+" is NOT found!";
    }

    public String findByPolicyNumbers(Set<String> policyNumbers){
        return toString(insuranceDbService.findByPolicyNumbers(policyNumbers).stream(),Object::toString);
    }

    public  String findByInsured(String insured){
        return toString(insuranceDbService.findByInsured(insured).stream(),Object::toString);
    }

    public String findByInsuranceMonth(Month month){
        return  toString(insuranceDbService.findByInsuranceMonth(month).stream(),Object::toString);
    }

    public <T> String toString(Stream<T> stream, Function<T,? extends String> tooStringMapper){
        return stream.map(tooStringMapper).collect(Collectors.joining("\t"));
    }
}
