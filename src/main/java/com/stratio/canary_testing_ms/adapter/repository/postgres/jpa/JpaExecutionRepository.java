package com.stratio.canary_testing_ms.adapter.repository.postgres.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.ExecutionEntity;

public interface JpaExecutionRepository extends JpaRepository<ExecutionEntity, Long> {

}
