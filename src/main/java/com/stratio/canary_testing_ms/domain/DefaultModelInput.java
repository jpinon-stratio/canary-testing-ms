package com.stratio.canary_testing_ms.domain;

import com.stratio.canary_testing_ms.port.ModelInput;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class DefaultModelInput implements ModelInput {

  private Integer nTransactions;
  private Double meanAmount;
}
