package com.stratio.canary_testing_ms.adapter.repository.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.stratio.canary_testing_ms.port.ModelRepository;

import lombok.extern.slf4j.Slf4j;

//TODO: to be tested
@Repository
@Slf4j
public class ModelRestRepository implements ModelRepository {

  private final RestTemplate restTemplate;

  @Value("${canary.model.url}")
  private String repositoryUrl;

  public ModelRestRepository(){
    this.restTemplate = new RestTemplate();
  }

  public Map executeModel(Map input){

    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity request = new HttpEntity(input, headers);

    log.info("Sending request to intelligence model with url: {}, headers: {}", repositoryUrl, request.getHeaders().toString());
    log.info("Body -> {}", input.toString());

    ResponseEntity<Map> response = restTemplate.exchange(repositoryUrl, HttpMethod.POST, request, Map.class);

    log.info("Response body: {}", response.getBody().toString());

    Map output = response.getBody();

    output.put("modelId", response.getHeaders().get("x-canary-tracker").stream().findFirst().orElseThrow(RuntimeException::new));

    log.info("Output: {}", output.toString());

    return output ;
  }
}
