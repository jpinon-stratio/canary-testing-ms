package com.stratio.canary_testing_ms.adapter.controller.kafka;

import java.io.IOException;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.port.ModelRunnerProvider;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class KafkaController {

  private final ModelRunnerProvider modelRunnerProvider;
  private final ObjectMapper objectMapper;

  public KafkaController(ModelRunnerProvider modelRunnerProvider, ObjectMapper objectMapper){
    this.modelRunnerProvider = modelRunnerProvider;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "${message.topic.name:ms_input}", groupId = "${message.group.name:barclaysventures}")
  public void kafkaListener(String message){
    log.info("New Kafka event with message: {}", message);
    Map<String,Object> inputMap = null;

    try {
      inputMap = objectMapper.readValue(message, Map.class);

    }catch (IOException e){

    }

    modelRunnerProvider.run(new DefaultModelInput((Integer)inputMap.get("number_transactions"), (Double)inputMap.get("mean_transactions")));

    log.info("Kafka event processed correctly");
  }

}
