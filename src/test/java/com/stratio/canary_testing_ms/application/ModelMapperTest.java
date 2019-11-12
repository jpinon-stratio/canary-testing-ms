package com.stratio.canary_testing_ms.application;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.domain.DefaultModelOutput;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;

import static org.junit.Assert.*;


public class ModelMapperTest {


  @Test
  public void whenInputIsValid_thenModelInputIsCreated(){
    String json = "{\"schema\": {\"fields\": [{\"name\": \"nTransactions\", \"type\": \"integer\"},{\"name\": \"meanAmount\",\"type\": \"double\"}]},\"rows\": [[18, 585.5555555555555]]}";
    Map<String,Object> map = null;
    try {
      map = new ObjectMapper().readValue(json, Map.class);

    }catch (IOException e){

    }

    ModelInput modelInput = ModelMapper.toModelInput(map);

    assertTrue(modelInputsAreEquals(modelInput, new DefaultModelInput(18, 585.5555555555555)));
  }

  @Test
  public void whenOutputIsValid_thenModelOutputIsCreated(){
    String json = "{\"schema\":{\"fields\":[{\"name\":\"nTransactions\",\"type\":\"integer\"},{\"name\":\"meanAmount\",\"type\":\"double\"},{\"name\":\"features_ns\",\"type\":{\"type\":\"tensor\",\"base\":\"double\",\"dimensions\":[2]}},{\"name\":\"features\",\"type\":{\"type\":\"tensor\",\"base\":\"double\",\"dimensions\":[2]}},{\"name\":\"prediction\",\"type\":{\"type\":\"basic\",\"base\":\"integer\",\"isNullable\":false}}]},\"rows\":[[18,585.5555555555555,{\"values\":[18.0,585.5555555555555],\"dimensions\":[2]},{\"values\":[0.35714285714285715,0.7810703123136019],\"dimensions\":[2]},1]]}";
    Map<String,Object> map = null;
    try {
      map = new ObjectMapper().readValue(json, Map.class);
      map.put("modelId", "model1");
    }catch (IOException e){

    }

    ModelOutput modelOutput = ModelMapper.toModelOutput(map);

    Double[] expectedFeaturesNs = {18.0, 585.5555555555555};
    Double[] expectedFeatures = {0.35714285714285715, 0.7810703123136019};

    assertTrue(modelOutputsAreEquals(modelOutput, new DefaultModelOutput("model1", 18, 585.5555555555555, expectedFeaturesNs, expectedFeatures, 1)));
  }

  @Test
  public void whenModelInputIsValid_thenInputMapIsCreated(){
    String json = "{\"schema\": {\"fields\": [{\"name\": \"nTransactions\", \"type\": \"integer\"},{\"name\": \"meanAmount\",\"type\": \"double\"}]},\"rows\": [[18, 585.5555555555555]]}";
    Map<String,Object> expectedMap = null;
    try {
      expectedMap = new ObjectMapper().readValue(json, Map.class);
    }catch (IOException e){

    }

    Map<String,Object> retrievedMap = ModelMapper.toInputMap(new DefaultModelInput(18, 585.5555555555555));

    assertTrue(expectedMap.equals(retrievedMap));
  }


  private boolean modelInputsAreEquals(ModelInput expected, ModelInput result){
    return expected.getNTransactions().equals(result.getNTransactions()) &&
        expected.getMeanAmount().equals(result.getMeanAmount());
  }

  private boolean modelOutputsAreEquals(ModelOutput expected, ModelOutput result){
    return expected.getNTransactions().equals(result.getNTransactions()) &&
        expected.getMeanAmount().equals(result.getMeanAmount()) &&
        expected.getModelId().equals(result.getModelId()) &&
        expected.getPrediction().equals(result.getPrediction());
  }
}
