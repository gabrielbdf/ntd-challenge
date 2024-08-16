package com.ntd.project.service.impl;

import com.ntd.project.model.OperationRecord;
import com.ntd.project.repository.OperationRecordRepository;
import com.ntd.project.security.model.UserModel;
import com.ntd.project.service.OperationRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OperationRecordServiceImpl implements OperationRecordService {

    private final OperationRecordRepository operationRecordRepository;

    @Override
    public List<OperationRecord> getRecords(UserModel user) {
        return this.operationRecordRepository.getByUser(user);
    }
}
