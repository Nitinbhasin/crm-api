package com.prudential.phi.operation.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCasesResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	String email;
	String phone;
	String subject;
	String description;
	String origin;
	String ownerId;
	String status;
	Boolean isClosed;
	String caseNumber;
	String caseId;
	String createdDate;
	String lastModifiedDate;
	String closedDate;
}
