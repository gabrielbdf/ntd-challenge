package com.ntd.project.service.impl;

import com.ntd.project.security.service.UserService;
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
    private final UserService userService;

    @Override
    public OperationResponse operate(OperationRequest operationRequest, String username) {
        var user = userService.getUserDetais(username);
        var operation = operationRepository.findByOperation(operationRequest.operation()).orElseThrow();

        try {
            user = userService.decreaseUserBalance(user, operation.getCost());
            if (Operation.RAMDOM_STRING.equals(operationRequest.operation())) {
                return new OperationResponse(
                        true,
                        operationRequest.operation().name(),
                        getRandomString(), user.getBalance());
            }
            binding.setVariable("args", operationRequest.args());
            GroovyShell shell = new GroovyShell(binding);
            OperationModel operationModel = operationRepository.findByOperation(operationRequest.operation()).get();
            OperationResponse result = new OperationResponse(true, operationRequest.operation().name(),
                    (String) shell.evaluate(operationModel.getGroovyExpression()), user.getBalance());
            operationRecordRepository.saveResult(
                    result,
                    operationRequest,
                    operation,
                    user);
            return result;
        } catch (IllegalStateException e) {
            return new OperationResponse(false, operationRequest.operation().name(), e.getLocalizedMessage(), user.getBalance());
        }
    }

    public static String getRandomString() {
        return restTemplate.getForObject(randomStringUrl, String.class);
    }

}
