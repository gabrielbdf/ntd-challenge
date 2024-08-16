package com.ntd.project.service;

import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
import groovy.lang.Binding;
import org.springframework.web.client.RestTemplate;

public interface OperationService {

    RestTemplate restTemplate = new RestTemplate();
    Binding binding = new Binding();
    String randomStringUrl = "https://www.random.org/strings/?num=1&len=7&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new";


    OperationResponse operate(OperationRequest operationRequest, String username);


}
