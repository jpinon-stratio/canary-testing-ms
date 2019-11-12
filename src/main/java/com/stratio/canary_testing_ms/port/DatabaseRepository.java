package com.stratio.canary_testing_ms.port;

public interface DatabaseRepository {
  void saveExecutionResults(ModelInput modelInput, ModelOutput modelOutput);
}
