package com.ntd.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;

import groovy.lang.Binding;

@Service
public interface OperationService {

    public static final RestTemplate restTemplate = new RestTemplate();
    public static final Binding binding = new Binding();
    static String randomStringUrl = "https://www.random.org/strings/?num=1&len=7&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new";


    public OperationResponse operate(OperationRequest operationRequest, String username);

}
