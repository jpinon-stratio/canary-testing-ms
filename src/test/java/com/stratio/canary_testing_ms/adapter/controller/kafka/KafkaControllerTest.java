package com.stratio.canary_testing_ms.adapter.controller.kafka;

import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.port.ModelInput;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Ignore
public class KafkaControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void whenMessageInput_thenModelInputIsCreated(){
    String kafkaMessage = "{\"account_id\": 123123,\"mean_transactions\": 555.555,\"number_transactions\": 18}";

    // KafkaController logic

    Map<String,Object> inputMap = null;

    try {
      inputMap = objectMapper.readValue(kafkaMessage, Map.class);

    }catch (IOException e){

    }

    ModelInput modelInput = new DefaultModelInput((Integer)inputMap.get("number_transactions"), (Double)inputMap.get("mean_transactions"));

    int i=0;
  }
}
