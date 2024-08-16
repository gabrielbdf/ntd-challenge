package com.ntd.project.service.impl;

import com.ntd.project.dto.Operation;
import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class OperationServiceGroovyTest {

    @Mock
    private OperationRecordRepository operationRecordRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OperationServiceGroovy operationServiceGroovy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOperate_withRandomStringOperation() {

        OperationRequest request = mock(OperationRequest.class);
        when(request.operation()).thenReturn(Operation.RAMDOM_STRING);

        String username = "testUser";

        OperationResponse response = operationServiceGroovy.operate(request, username);


        assertEquals(Operation.RAMDOM_STRING.name(), response.operation());
        assertNotNull(response.result());
        verify(operationRecordRepository, never()).saveResult(any(), any(), any(), any());
    }

    @Test
    public void testOperate_withGroovyOperation() {
        // Arrange
        OperationRequest request = new OperationRequest(Operation.ADD, 2L, 3L);
        OperationModel model = OperationModel.builder()
                .cost(10L)
                .operation(Operation.ADD)
                .groovyExpression("args.collect{it.toLong()}.sum().toString()")
                .build();
        when(operationRepository.findByOperation(Operation.ADD)).thenReturn(Optional.of(model));
        when(userRepository.findByUsername(anyString())).thenReturn(mock(UserModel.class));

        String username = "testUser";

        // Act
        OperationResponse response = operationServiceGroovy.operate(request, username);

        // Assert
        assertEquals("5", response.result());
        verify(operationRecordRepository).saveResult(any(), any(), any(), any());
    }

    @Test
    public void testGetRandomString() {
        String randomString = OperationServiceGroovy.getRandomString();
        assertNotNull(randomString);
    }
}