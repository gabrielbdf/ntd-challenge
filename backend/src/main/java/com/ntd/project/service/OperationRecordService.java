package com.ntd.project.service;

import com.ntd.project.model.OperationRecord;
import com.ntd.project.security.model.UserModel;

import java.util.List;

public interface OperationRecordService {

    List<OperationRecord> getRecords(UserModel user);

}
