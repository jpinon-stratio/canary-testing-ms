package com.stratio.canary_testing_ms.adapter.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.port.ModelRunnerProvider;

@RestController
public class ModelRestController {

  private final ModelRunnerProvider modelRunnerProvider;

  @Autowired
  public ModelRestController(ModelRunnerProvider modelRunnerProvider){
    this.modelRunnerProvider = modelRunnerProvider;
  }

  @PostMapping("/run-model")
  public ResponseEntity runModel(@RequestBody DefaultModelInput input){
    return new ResponseEntity(modelRunnerProvider.run(input), HttpStatus.OK);
  }
}
