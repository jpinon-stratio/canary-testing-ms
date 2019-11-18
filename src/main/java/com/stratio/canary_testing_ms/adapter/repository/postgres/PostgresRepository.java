package com.stratio.canary_testing_ms.adapter.repository.postgres;

import org.springframework.stereotype.Repository;

import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.ExecutionEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaExecutionRepository;
import com.stratio.canary_testing_ms.port.DatabaseRepository;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostgresRepository implements DatabaseRepository {

  private final JpaExecutionRepository jpaExecutionRepository;

  public PostgresRepository(JpaExecutionRepository jpaExecutionRepository){
    this.jpaExecutionRepository = jpaExecutionRepository;
  }

  @Override
  public void saveExecutionResults(ModelInput modelInput, ModelOutput modelOutput) {


    ExecutionEntity executionEntity = new ExecutionEntity(modelOutput.getModelId(), modelInput.getNTransactions(), modelInput.getMeanAmount(), modelOutput.getFeaturesNs(), modelOutput.getFeatures(), modelOutput.getPrediction());

    jpaExecutionRepository.save(executionEntity);

    log.info("Entities stored in database");
  }
}
