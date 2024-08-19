package com.ntd.project.model;

import com.ntd.project.dto.Operation;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "operation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OperationModel {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    private Long cost;

    private String groovyExpression;

}
