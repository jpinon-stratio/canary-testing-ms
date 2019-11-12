package com.stratio.canary_testing_ms.port;

import java.util.Map;

public interface ModelRepository {
  Map executeModel(Map modelInput);
}
