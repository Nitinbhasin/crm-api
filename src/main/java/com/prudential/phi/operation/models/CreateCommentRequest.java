package com.prudential.phi.operation.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCommentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String body;
	String caseId;
	Boolean isPublic;

}
