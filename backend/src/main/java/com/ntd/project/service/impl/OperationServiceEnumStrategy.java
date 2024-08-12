package com.ntd.project.service.impl;

import org.springframework.stereotype.Service;

import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
import com.ntd.project.repository.OperationRecordRepository;
import com.ntd.project.repository.OperationRepository;
import com.ntd.project.security.repository.UserRepository;
import com.ntd.project.service.OperationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperationServiceEnumStrategy implements OperationService {

    private final OperationRecordRepository operationRecordRepository;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;


    @Override
    public OperationResponse operate(OperationRequest operationRequest, String username) {
        OperationResponse result = new OperationResponse(operationRequest.operation().name(),
                operationRequest.operation().calculate(operationRequest.args()));
        
        operationRecordRepository.saveResult(
                result,
                operationRequest,
                operationRepository.findByOperation(operationRequest.operation()).get(),
                userRepository.findByUsername(username));
        return result;
    }

}
