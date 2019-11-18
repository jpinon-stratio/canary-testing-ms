CREATE SCHEMA IF NOT EXISTS canary;

CREATE TABLE IF NOT EXISTS canary.execution_model (
  id BIGSERIAL NOT NULL,
  model_id VARCHAR(50) NOT NULL,
  n_transactions INTEGER NOT NULL,
  mean_amount FLOAT8 NOT NULL,
  features_ns FLOAT8[] NOT NULL,
  features FLOAT8[] NOT NULL,
  prediction INTEGER NOT NULL,
  PRIMARY KEY (id)
);


