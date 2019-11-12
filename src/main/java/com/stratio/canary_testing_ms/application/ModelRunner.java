package com.stratio.canary_testing_ms.application;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.stratio.canary_testing_ms.port.DatabaseRepository;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;
import com.stratio.canary_testing_ms.port.ModelRepository;
import com.stratio.canary_testing_ms.port.ModelRunnerProvider;

@Service
public class ModelRunner implements ModelRunnerProvider {

  private final DatabaseRepository databaseRepository;
  private final ModelRepository modelRepository;

  public ModelRunner (DatabaseRepository databaseRepository, ModelRepository modelRepository) {
    this.databaseRepository = databaseRepository;
    this.modelRepository = modelRepository;
  }

  public ModelOutput run(ModelInput modelInput){

    Map input = ModelMapper.toInputMap(modelInput);

    Map output = modelRepository.executeModel(input);

    ModelOutput modelOutput = ModelMapper.toModelOutput(output);

    databaseRepository.saveExecutionResults(modelInput, modelOutput);

    return modelOutput;
  }

}
