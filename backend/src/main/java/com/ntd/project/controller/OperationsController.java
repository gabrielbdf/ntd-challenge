package com.ntd.project.controller;

import com.ntd.project.dto.OperationHistory;
import com.ntd.project.dto.OperationList;
import com.ntd.project.model.OperationRecord;
import com.ntd.project.security.service.UserService;
import com.ntd.project.service.OperationRecordService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/operation")
@Tag(name = "Operations")
@AllArgsConstructor
public class OperationsController {

    private final OperationServiceEnumStrategy operationServiceEnumStrategy;
    private final OperationServiceGroovy operationServiceGroovy;
    private final UserService userService;
    private final OperationRecordService operationRecordService;


    @Operation(summary = "Operation", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/list")
    public OperationList getOperations(
            @AuthenticationPrincipal UserDetails userDetails) {
        var username = userDetails.getUsername();
        var user = userService.getUserDetais(username);
        com.ntd.project.dto.Operation[] operations = com.ntd.project.dto.Operation.values();
        List<List<String>> operationList = Streams.of(operations).map(i -> List.of(i.name(), i.numOfArgs().toString())).collect(Collectors.toList());
        return new OperationList(operationList, new com.ntd.project.dto.UserDetails(username, user.getBalance()));
    }

    @Operation(summary = "Operation", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/history")
    public List<OperationHistory> getUserHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer>  perPage
    ) {
        var username = userDetails.getUsername();
        var user = userService.getUserDetais(username);
        return operationRecordService
                .getRecords(user, page, perPage)
                .stream().map(OperationHistory::from)
                .toList();
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
