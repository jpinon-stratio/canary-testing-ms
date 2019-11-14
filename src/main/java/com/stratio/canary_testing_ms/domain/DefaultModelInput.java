package com.stratio.canary_testing_ms.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stratio.canary_testing_ms.port.ModelInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DefaultModelInput implements ModelInput {

  @JsonProperty("nTransactions")
  private Integer nTransactions;
  private Double meanAmount;
}
