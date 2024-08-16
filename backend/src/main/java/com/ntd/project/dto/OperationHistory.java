package com.ntd.project.dto;

import com.ntd.project.model.OperationRecord;
import com.ntd.project.utils.DateUtils;
import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistory {
    private Long id;
    private String operation;
    private Long amount;
    private String response;
    private String timestamp;
    private Long userBalance;

    public static OperationHistory from(OperationRecord record) {
        return OperationHistory.builder()
                .id(record.getId())
                .operation(record.getOperation().getOperation().name())
                .amount(record.getAmount())
                .response(record.getOperatioResponse())
                .timestamp(DateUtils.formatDate(record.getCreatedAt()))
                .userBalance(record.getUserBalance())
                .build();
    }
}
