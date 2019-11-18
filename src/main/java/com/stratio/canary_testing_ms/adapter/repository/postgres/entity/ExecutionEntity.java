package com.stratio.canary_testing_ms.adapter.repository.postgres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "execution_model", schema = "canary")
@NoArgsConstructor
@Getter
@Setter
public class ExecutionEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "model_id")
  private String modelId;

  @Column(name = "n_transactions", nullable = false)
  private Integer nTransactions;

  @Column(name = "mean_amount", nullable = false)
  private Double meanAmount;

  @Column(name = "features_ns", nullable = false)
  @Type(type = "com.stratio.canary_testing_ms.adapter.repository.postgres.type.GenericArrayUserType")
  private Double[] featuresNs;

  @Column(name = "features", nullable = false)
  @Type(type = "com.stratio.canary_testing_ms.adapter.repository.postgres.type.GenericArrayUserType")
  private Double[] features;

  @Column(name = "prediction", nullable = false)
  private Integer prediction;

  public ExecutionEntity(String modelId, Integer nTransactions, Double meanAmount, Double[] featuresNs,
      Double[] features, Integer prediction) {
    this.modelId = modelId;
    this.nTransactions = nTransactions;
    this.meanAmount = meanAmount;
    this.featuresNs = featuresNs;
    this.features = features;
    this.prediction = prediction;
  }
}
