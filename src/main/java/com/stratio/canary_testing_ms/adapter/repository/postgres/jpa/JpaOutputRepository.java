package com.stratio.canary_testing_ms.adapter.repository.postgres.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.InputEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.OutputEntity;

public interface JpaOutputRepository extends JpaRepository<OutputEntity, Long> {

}
