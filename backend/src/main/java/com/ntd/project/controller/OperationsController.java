package com.ntd.project.controller;

import org.apache.commons.lang3.stream.Streams;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
import com.ntd.project.service.impl.OperationServiceEnumStrategy;
import com.ntd.project.service.impl.OperationServiceGroovy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/operation")
@Tag(name = "Operations")
@AllArgsConstructor
public class OperationsController {

    private final OperationServiceEnumStrategy operationServiceEnumStrategy;
    private final OperationServiceGroovy operationServiceGroovy;


    @Operation(summary = "Operation")
    @GetMapping("/list")
    public List<List<String>> getOperations() {
        com.ntd.project.dto.Operation[] operations = com.ntd.project.dto.Operation.values();
        return Streams.of(operations).map(i -> List.of(i.name(), i.numOfArgs().toString())).collect(Collectors.toList());
    }

    @Operation(summary = "Operation", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/enum")
    public OperationResponse operateByEnumStrategy(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OperationRequest operationRequest) {
        return operationServiceEnumStrategy.operate(operationRequest, userDetails.getUsername());
    }

    @Operation(summary = "TestEndpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/groovy")
    public OperationResponse operateByGroovyStrategy(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OperationRequest operationRequest) {
        return operationServiceGroovy.operate(operationRequest, userDetails.getUsername());
    }

}
