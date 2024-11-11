package com.prudential.phi.operation.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@SerializedName("name")
	private String name = null;
	
	@SerializedName("email")
	private String email = null;
	
	@SerializedName("phone")
	private String phone = null;
	
	@SerializedName("description")
	private String description = null;
	
	@SerializedName("origin")
	private String origin = null;
	
	@SerializedName("subject")
	private String subject = null;
	
	@SerializedName("documents")
	private List<Document> documents = null;
	
	


}
