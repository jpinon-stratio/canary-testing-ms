package com.stratio.canary_testing_ms.port;

public interface ModelOutput{
  Integer getNTransactions();
  Double getMeanAmount();
  Double[] getFeaturesNs();
  Double[] getFeatures();
  Integer getPrediction();
  String getModelId();
}
