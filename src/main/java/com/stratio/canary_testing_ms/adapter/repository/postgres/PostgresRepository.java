package com.stratio.canary_testing_ms.adapter.repository.postgres;

import org.springframework.stereotype.Repository;

import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.ExecutionEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.InputEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.OutputEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaExecutionRepository;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaInputRepository;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaOutputRepository;
import com.stratio.canary_testing_ms.port.DatabaseRepository;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostgresRepository implements DatabaseRepository {

  private final JpaExecutionRepository jpaExecutionRepository;
  private final JpaInputRepository jpaInputRepository;
  private final JpaOutputRepository jpaOutputRepository;

  public PostgresRepository(JpaExecutionRepository jpaExecutionRepository, JpaInputRepository jpaInputRepository, JpaOutputRepository jpaOutputRepository){
    this.jpaExecutionRepository = jpaExecutionRepository;
    this.jpaInputRepository = jpaInputRepository;
    this.jpaOutputRepository = jpaOutputRepository;
  }

  @Override
  public void saveExecutionResults(ModelInput modelInput, ModelOutput modelOutput) {

    InputEntity inputEntity = new InputEntity(modelInput);
    OutputEntity outputEntity = new OutputEntity(modelOutput);
    ExecutionEntity executionEntity = new ExecutionEntity(modelOutput.getModelId(), inputEntity, outputEntity);

    jpaInputRepository.save(inputEntity);
    jpaOutputRepository.save(outputEntity);
    jpaExecutionRepository.save(executionEntity);

    log.info("Entities stored in database");
  }
}
