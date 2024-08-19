package com.ntd.project.service.impl;

import com.ntd.project.model.OperationRecord;
import com.ntd.project.repository.OperationRecordRepository;
import com.ntd.project.security.model.UserModel;
import com.ntd.project.service.OperationRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OperationRecordServiceImpl implements OperationRecordService {

    private final OperationRecordRepository operationRecordRepository;

    @Override
    public List<OperationRecord> getRecords(UserModel user, Optional<Integer> page, Optional<Integer> perPage) {
        PageRequest request = PageRequest.of(page.orElse(0), perPage.orElse(100), Sort.Direction.DESC, "createdAt");
        return this.operationRecordRepository.getByUser(user, request);
    }
}
