package com.ntd.project.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntd.project.dto.OperationRequest;
import com.ntd.project.dto.OperationResponse;
import com.ntd.project.model.OperationModel;
import com.ntd.project.model.OperationRecord;
import com.ntd.project.security.model.UserModel;

@Repository
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Long> {

    public default void saveResult(OperationResponse result, OperationRequest operationRequest,
                                   OperationModel operationModel, UserModel userModel) {
        if (result != null) {
            OperationRecord operationRecord = new OperationRecord();
            operationRecord.setOperation(operationModel);
            operationRecord.setUser(userModel);
            operationRecord.setAmount(operationModel.getCost());
            operationRecord.setUserBalance(userModel.getBalance());
            operationRecord.setOperatioResponse(result.result());
            operationRecord.setCreatedAt(new Date());
            save(operationRecord);
        }
    }

}
