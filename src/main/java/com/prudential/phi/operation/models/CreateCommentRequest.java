package com.prudential.phi.operation.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCommentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	String body;
	
	@NotNull
	@NotEmpty
	String caseId;
	
	Boolean isPublic;

}
