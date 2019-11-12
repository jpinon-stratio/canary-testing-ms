package com.stratio.canary_testing_ms.adapter.repository.postgres;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.ExecutionEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.InputEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.entity.OutputEntity;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaExecutionRepository;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaInputRepository;
import com.stratio.canary_testing_ms.adapter.repository.postgres.jpa.JpaOutputRepository;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.domain.DefaultModelOutput;
import com.stratio.canary_testing_ms.port.DatabaseRepository;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@Sql(executionPhase= ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema-test.sql")
public class PostgresRepositoryTest {

  @Autowired
  DatabaseRepository databaseRepository;

  @Autowired
  JpaOutputRepository jpaOutputRepository;

  @Autowired
  JpaInputRepository jpaInputRepository;

  @Autowired
  JpaExecutionRepository jpaExecutionRepository;

  @Test
  public void whenDataIsOk_thenRowIsCreated(){
    Double[] expectedFeaturesNs = {18.0, 585.5555555555555};
    Double[] expectedFeatures = {0.35714285714285715, 0.7810703123136019};
    ModelOutput modelOutput = new DefaultModelOutput("model1", 18, 585.5555555555555, expectedFeaturesNs, expectedFeatures, 1);
    ModelInput modelInput = new DefaultModelInput(18, 585.5555555555555);

    databaseRepository.saveExecutionResults(modelInput, modelOutput);

    InputEntity inputEntity = jpaInputRepository.findAll().stream().findFirst().get();
    OutputEntity outputEntity = jpaOutputRepository.findAll().stream().findFirst().get();
    ExecutionEntity executionEntity = jpaExecutionRepository.findAll().stream().findFirst().get();

    int i = 0;
  }
}
