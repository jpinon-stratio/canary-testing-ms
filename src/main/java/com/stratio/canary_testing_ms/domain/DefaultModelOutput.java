package com.stratio.canary_testing_ms.domain;

import com.stratio.canary_testing_ms.port.ModelOutput;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class DefaultModelOutput implements ModelOutput{
  private String modelId;
  private Integer nTransactions;
  private Double meanAmount;
  private Double[] featuresNs;
  private Double[] features;
  private Integer prediction;

}
