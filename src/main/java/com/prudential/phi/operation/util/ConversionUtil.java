package com.prudential.phi.operation.util;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConversionUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObjectResponse(String responseBody, Class valueType) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JsonNullableModule());
		return mapper.readValue(responseBody, valueType);
	}

}
