package com.apiService.apiHospital.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiHelper {
    private final RestTemplate template;

    public <T> ResponseEntity<T> request(String url,Map<String,Object> params,Class<T> type){
        try {
            return template.exchange(url, HttpMethod.GET,null,type,params);
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    public <T> ResponseEntity<List<T>> requestLst(String url,Map<String,Object> params,Class<T> type){
        ParameterizedTypeReference<List<T>> kst = new ParameterizedTypeReference<List<T>>() {};
        try {
            return template.exchange(url, HttpMethod.GET,null,kst,params);
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

   
}
