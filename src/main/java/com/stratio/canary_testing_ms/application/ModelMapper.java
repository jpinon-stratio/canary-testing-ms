package com.stratio.canary_testing_ms.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.canary_testing_ms.domain.DefaultModelInput;
import com.stratio.canary_testing_ms.domain.DefaultModelOutput;
import com.stratio.canary_testing_ms.port.ModelInput;
import com.stratio.canary_testing_ms.port.ModelOutput;

public class ModelMapper {

  private static ObjectMapper mapper = new ObjectMapper();

  private static final String JSON_SCHEMA = "{\"schema\": {\"fields\": [{\"name\": \"nTransactions\", \"type\": \"integer\"},{\"name\": \"meanAmount\",\"type\": \"double\"}]},\"rows\": [[18, 585.5555555555555]]}";


  public static ModelOutput toModelOutput(Map map){

    String modelId = (mapper.convertValue(map.get("modelId"), String.class));

    List list = (List)(mapper.convertValue(map.get("rows"), List.class)).get(0);

    Integer nTransactions = (Integer)list.get(0);

    Double meanAmount = (Double)list.get(1);

    Map<String, List<Double>> featuresNsMap = (Map)list.get(2);

    Double[] featuresNs = featuresNsMap.get("values").stream().toArray(Double[]::new);

    Map<String, List<Double>> featuresMap = (Map)list.get(3);

    Double[] features = featuresMap.get("values").stream().toArray(Double[]::new);

    Integer prediction = (Integer)list.get(4);

    return new DefaultModelOutput(modelId, nTransactions, meanAmount, featuresNs, features, prediction);
  }

  public static ModelInput toModelInput(Map map){

    List list = (List)(mapper.convertValue(map.get("rows"), List.class)).get(0);

    return new DefaultModelInput((Integer)list.get(0), (Double)list.get(1));
  }

  public static Map toInputMap(ModelInput modelInput){
    Map<String,Object> map = null;
    try {
      map = new ObjectMapper().readValue(JSON_SCHEMA, Map.class);
    }catch (IOException e){

    }

    List<List> rowsList = new ArrayList<>();

    rowsList.add(new ArrayList());

    rowsList.get(0).add(modelInput.getNTransactions());

    rowsList.get(0).add(modelInput.getMeanAmount());

    map.replace("rows", rowsList);

    return map;
  }
}
