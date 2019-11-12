package com.stratio.canary_testing_ms.adapter.repository.postgres.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.stratio.canary_testing_ms.port.ModelInput;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "input_model")
@NoArgsConstructor
@Getter
@Setter
public class InputEntity implements ModelInput {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "n_transactions", nullable = false)
  private Integer nTransactions;

  @Column(name = "mean_amount", nullable = false)
  private Double meanAmount;

  @OneToOne(mappedBy = "inputEntity", cascade = CascadeType.ALL)
  private ExecutionEntity executionEntity;

  public InputEntity(Integer nTransactions, Double meanAmount,
      ExecutionEntity executionEntity) {
    this.nTransactions = nTransactions;
    this.meanAmount = meanAmount;
    this.executionEntity = executionEntity;
  }

  public InputEntity(ModelInput modelInput){
    nTransactions = modelInput.getNTransactions();
    meanAmount = modelInput.getMeanAmount();
  }
}
