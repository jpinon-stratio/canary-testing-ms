package com.stratio.canary_testing_ms.adapter.controller.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class KafkaControllerTest {

  @Autowired
  private KafkaController kafkaController;

  @Test
  public void whenMessageInput_thenModelInputIsCreated(){
    kafkaController.kafkaListener("{\"account_id\": 123123,\"mean_transactions\": 555.555,\"number_transactions\": 18}");
  }
}
