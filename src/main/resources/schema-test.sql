SET DATABASE SQL SYNTAX PGS TRUE;

DROP TABLE IF EXISTS execution;
DROP TABLE IF EXISTS input_model;
DROP TABLE IF EXISTS output_model;

CREATE TABLE output_model (
  id BIGSERIAL NOT NULL,
  n_transactions INTEGER NOT NULL,
  mean_amount DOUBLE NOT NULL,
  features_ns DOUBLE[] NOT NULL,
  features DOUBLE[] NOT NULL,
  prediction INTEGER NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE input_model (
  id BIGSERIAL NOT NULL,
  n_transactions INTEGER NOT NULL,
  mean_amount DOUBLE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE execution (
  id BIGSERIAL NOT NULL,
  model_id VARCHAR(50) NOT NULL,
  fk_output BIGSERIAL REFERENCES output_model(id),
  fk_input BIGSERIAL REFERENCES input_model(id),
  PRIMARY KEY (id),
);

