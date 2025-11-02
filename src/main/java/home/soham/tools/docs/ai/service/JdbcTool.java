package home.soham.tools.docs.ai.service;

import home.soham.tools.docs.ai.model.Insurance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JdbcTool {
    private final JdbcTemplate jdbcTemplate;


    @Tool(name = "get_policyDetails", description = "Always use this tool to find policy details",returnDirect = true)
    public Object getPolicyDetails(@ToolParam( description = "policy number of the policy") String policy_number){
        log.info("Executing findInsuranceByPolicyNumber>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Object result="I do not know. Sorry!";

        try {
            var policyResult= jdbcTemplate.queryForObject("SELECT * " +
                    "FROM public.tbl_insurance where policy_no=?", new BeanPropertyRowMapper<>(Insurance.class), policy_number);

            result = policyResult.toString();
        } catch (DataAccessException e) {
            log.error("Error executing query",e.fillInStackTrace());
            result="i do not know if  policy really exists";
        }

        return result;

    }

    @Tool(name = "get_policies", description = "Always use this tool to find all policies",returnDirect = true)
    public String getPolicyDetails(){
        log.info("Executing getPolicyDetails >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String result="I do not know. Sorry!";

        try {
            List<Insurance> policyResult = jdbcTemplate.query("SELECT * " +
                    "FROM public.tbl_insurance", new BeanPropertyRowMapper<Insurance>(Insurance.class));

            result = policyResult.toString();
        } catch (DataAccessException e) {
            log.error("Error executing query",e.fillInStackTrace());
            result="i do not know if  policy really exists";
        }

        return result;

    }
}
