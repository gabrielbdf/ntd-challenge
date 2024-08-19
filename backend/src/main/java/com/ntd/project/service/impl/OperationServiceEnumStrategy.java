package com.ntd.project.service.impl;

import com.ntd.project.security.service.UserService;
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
    private final UserService userService;


    @Override
    public OperationResponse operate(OperationRequest operationRequest, String username) {
        var user = userService.getUserDetais(username);
        try {
            var operation = operationRepository.findByOperation(operationRequest.operation())
                    .orElseThrow();
            var operationResult = operationRequest.operation().calculate(operationRequest.args());

            user = userService.decreaseUserBalance(user, operation.getCost());

            OperationResponse result = new OperationResponse(
                    true,
                    operationRequest.operation().name(),
                    operationResult,
                    user.getBalance()
            );

            operationRecordRepository.saveResult(
                    result,
                    operationRequest,
                    operation,
                    user);
            return result;
        } catch (IllegalStateException | ArithmeticException e) {
            return new OperationResponse(false, operationRequest.operation().name(), e.getLocalizedMessage(), user.getBalance());
        }
    }

}
