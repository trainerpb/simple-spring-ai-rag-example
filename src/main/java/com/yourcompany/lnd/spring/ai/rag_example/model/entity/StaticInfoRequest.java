package com.yourcompany.lnd.spring.ai.rag_example.model.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class StaticInfoRequest {





    public static Map<String,Set<String>> findByType(Map<String, Map<String, Set<String>>> info, String keyLevelA, String keyLevelB){
        if(CollectionUtils.isEmpty(info)){
            return Map.of();
        }

        for(String keyA: info.keySet()){
            if(StringUtils.containsIgnoreCase(keyA,keyLevelA) || StringUtils.containsIgnoreCase(keyLevelA,keyA)){
                Map<String, Set<String>> levelBMap = info.get(keyA);
                for(String keyB: levelBMap.keySet()){
                    if(StringUtils.containsIgnoreCase(keyB,keyLevelB) || StringUtils.containsIgnoreCase(keyLevelB,keyB)){
                        return Map.of(keyB, levelBMap.get(keyB));
                    }
                }
            }
        }
        return Map.of();
    }
}
