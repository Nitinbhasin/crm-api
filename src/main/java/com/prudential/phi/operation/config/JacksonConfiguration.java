package com.prudential.phi.operation.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {
    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
    
    
    @Bean
    @Primary
    public ObjectMapper objectMapper()
    {
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    	objectMapper.registerModule(new JsonNullableModule());
    	return objectMapper;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
   
}
