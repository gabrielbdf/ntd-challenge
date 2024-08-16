package com.ntd.project.service.impl;

import com.ntd.project.dto.Operation;
import com.ntd.project.dto.OperationRequest;
import com.ntd.project.model.OperationModel;
import com.ntd.project.repository.OperationRecordRepository;
import com.ntd.project.repository.OperationRepository;
import com.ntd.project.security.model.UserModel;
import com.ntd.project.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OperationServiceEnumStrategyTest {

    @Mock
    private OperationRecordRepository operationRecordRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OperationServiceEnumStrategy operationServiceEnumStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOperate_withEnumStrategyOperation() {

        OperationModel model = OperationModel.builder()
                .cost(10L)
                .operation(Operation.ADD)
                .groovyExpression("args.collect{it.toLong()}.sum().toString()")
                .build();

        when(operationRepository.findByOperation(Operation.ADD)).thenReturn(Optional.of(model));
        when(userRepository.findByUsername(anyString())).thenReturn(mock(UserModel.class));

        OperationRequest request = new OperationRequest(Operation.ADD, 2L, 3L);

        var response = operationServiceEnumStrategy.operate(request, "username");


        assertEquals("5", response.result());
        verify(operationRecordRepository).saveResult(any(), any(), any(), any());


    }
}