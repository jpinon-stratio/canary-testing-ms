package com.stratio.canary_testing_ms.port;

import java.util.Map;

public interface ModelRunnerProvider {
  ModelOutput run(ModelInput modelInput);
}
