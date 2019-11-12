package com.stratio.canary_testing_ms.adapter.repository.postgres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "execution_model")
@NoArgsConstructor
@Getter
@Setter
public class ExecutionEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "model_id", nullable = false)
  private String modelId;

  @OneToOne
  @JoinColumn(name = "fk_input", updatable = false, nullable = false)
  private InputEntity inputEntity;

  @OneToOne
  @JoinColumn(name = "fk_output", updatable = false, nullable = false)
  private OutputEntity outputEntity;

  public ExecutionEntity(String modelId,
      InputEntity inputEntity, OutputEntity outputEntity) {
    this.modelId = modelId;
    this.inputEntity = inputEntity;
    this.outputEntity = outputEntity;
  }

}
