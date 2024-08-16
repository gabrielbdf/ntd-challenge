package com.ntd.project.model;

import com.ntd.project.dto.Operation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "operation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    private Long cost;

    private String groovyExpression;

}
