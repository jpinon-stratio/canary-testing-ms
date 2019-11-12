package com.stratio.canary_testing_ms.adapter.repository.postgres.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.stratio.canary_testing_ms.port.ModelOutput;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "output_model")
@NoArgsConstructor
@Getter
@Setter
public class OutputEntity implements ModelOutput {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "n_transactions", nullable = false)
  private Integer nTransactions;

  @Column(name = "mean_amount", nullable = false)
  private Double meanAmount;

  @Column(name = "features_ns", nullable = false)
  private Double[] featuresNs;

  @Column(name = "features", nullable = false)
  private Double[] features;

  @Column(name = "prediction", nullable = false)
  private Integer prediction;

  @OneToOne(mappedBy = "outputEntity", cascade = CascadeType.ALL)
  private ExecutionEntity executionEntity;

  public OutputEntity(Integer nTransactions, Double meanAmount, Double[] featuresNs, Double[] features,
      Integer prediction,
      ExecutionEntity executionEntity) {
    this.nTransactions = nTransactions;
    this.meanAmount = meanAmount;
    this.featuresNs = featuresNs;
    this.features = features;
    this.prediction = prediction;
    this.executionEntity = executionEntity;
  }

  public OutputEntity(ModelOutput modelOutput){
    nTransactions = modelOutput.getNTransactions();
    meanAmount = modelOutput.getMeanAmount();
    featuresNs = modelOutput.getFeaturesNs();
    features = modelOutput.getFeatures();
    prediction = modelOutput.getPrediction();
  }

  public String getModelId(){
    return null;
  }
}
