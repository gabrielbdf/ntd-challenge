package com.ntd.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntd.project.dto.Operation;
import com.ntd.project.model.OperationModel;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<OperationModel, Integer> {
  Optional<OperationModel> findByOperation(Operation operation);
}
