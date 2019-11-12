package com.stratio.canary_testing_ms.adapter.controller.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelRunnerProvider;

@Controller
public class KafkaController {

  private final ModelRunnerProvider modelRunnerProvider;
  private final ObjectMapper objectMapper;

  public KafkaController(ModelRunnerProvider modelRunnerProvider, ObjectMapper objectMapper){
    this.modelRunnerProvider = modelRunnerProvider;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "${message.topic.name:uc_rt_view}", groupId = "${message.group.name:barclaysventure}")
  public void kafkaListener(String message){
    ModelInput modelInput = objectMapper.convertValue(message, DefaultModelInput.class);

    modelRunnerProvider.run(modelInput);
  }

}
