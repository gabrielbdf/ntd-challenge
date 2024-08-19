package com.ntd.project.service;

import com.ntd.project.model.OperationRecord;
import com.ntd.project.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface OperationRecordService {

    List<OperationRecord> getRecords(UserModel user, Optional<Integer> page, Optional<Integer> perPage);

}
