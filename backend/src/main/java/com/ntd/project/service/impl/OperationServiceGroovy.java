package com.ntd.project.service.impl;

import org.springframework.stereotype.Service;

import com.ntd.project.dto.Operation;
import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
import com.ntd.project.model.OperationModel;
import com.ntd.project.repository.OperationRecordRepository;
import com.ntd.project.repository.OperationRepository;
import com.ntd.project.security.repository.UserRepository;
import com.ntd.project.service.OperationService;

import groovy.lang.GroovyShell;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperationServiceGroovy implements OperationService {

    private final OperationRecordRepository operationRecordRepository;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    @Override
    public OperationResponse operate(OperationRequest operationRequest, String username) {
        if (Operation.RAMDOM_STRING.equals(operationRequest.operation())) {
            return new OperationResponse(operationRequest.operation().name(),
                    getRandomString());
        }
        binding.setVariable("args", operationRequest.args());
        GroovyShell shell = new GroovyShell(binding);
        OperationModel operationModel = operationRepository.findByOperation(operationRequest.operation()).get();
        OperationResponse result = new OperationResponse(operationRequest.operation().name(),
                (String) shell.evaluate(operationModel.getGroovyExpression()));
        operationRecordRepository.saveResult(
                result,
                operationRequest,
                operationRepository.findByOperation(operationRequest.operation()).get(),
                userRepository.findByUsername(username));
        return result;
    }

    public static String getRandomString() {
        return restTemplate.getForObject(randomStringUrl, String.class);
    }

}
