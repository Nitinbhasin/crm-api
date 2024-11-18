package com.prudential.phi.operation.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prudential.phi.operation.models.CreateCaseRequest;
import com.prudential.phi.operation.models.CreateCommentRequest;
import com.prudential.phi.operation.models.Document;
import com.prudential.phi.operation.models.Query;
import com.prudential.phi.operation.models.UpdateCaseRequest;
import com.prudential.phi.operation.models.UpdateCommentRequest;
import com.prudential.phi.operation.service.CRMService;
import com.prudential.phi.operation.util.ConversionUtil;
import com.prudential.phi.operation.util.RestUtil;

import io.swagger.annotations.ApiOperation;

@Validated
@RestController
@RequestMapping("/crm")
public class CRMApiController {

	@Autowired
	RestUtil restUtil;

	@Autowired
	ConversionUtil conversionUtil;

	@Autowired
	CRMService crmService;

	@ApiOperation(value = "", nickname = "createCase", notes = "Creates A New Case", tags = { "crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/createCase", produces = { "application/json" })
	public ResponseEntity<Object> createCase(@Valid @RequestBody CreateCaseRequest createCaseRequest) throws Exception {
		return ResponseEntity.ok(crmService.createSalesForceCase(createCaseRequest));
	}

	@ApiOperation(value = "", nickname = "queryCases", notes = "Fetches Cases based on Query", tags = { "crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/queryCases", produces = { "application/json" })
	public ResponseEntity<Object> queryCases(@Valid @RequestBody Query query) throws Exception {
		return ResponseEntity.ok(crmService.fetchCasesByQuery(query));
	}

	@ApiOperation(value = "", nickname = "updateCase", notes = "Update Case based on CaseID", tags = { "crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/updateCase/{id}", produces = { "application/json" })
	public ResponseEntity<Object> updateCase(@Valid @RequestBody UpdateCaseRequest updateCaseRequest,
			@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.updateCase(id, updateCaseRequest));
	}

	@ApiOperation(value = "", nickname = "updateComment", notes = "Update Comment based on CommentID", tags = {
			"crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/updateComment/{id}", produces = { "application/json" })
	public ResponseEntity<Object> updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest,
			@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.updateComment(id, updateCommentRequest));
	}

	@ApiOperation(value = "", nickname = "fetchCaseById", notes = "Fetch Case based on CaseID", tags = { "crm", })
	@RequestMapping(method = RequestMethod.GET, value = "/fetchCase/{id}", produces = { "application/json" })
	public ResponseEntity<Object> fetchCaseById(@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.fetchCaseByID(id));
	}

	@ApiOperation(value = "", nickname = "fetchCaseHistoryById", notes = "Fetch Case History based on CaseID", tags = {
			"crm", })
	@RequestMapping(method = RequestMethod.GET, value = "/fetchCaseHistory/{id}", produces = { "application/json" })
	public ResponseEntity<Object> fetchCaseHistory(@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.fetchCaseHistoryById(id));
	}

	@ApiOperation(value = "", nickname = "fetchCaseCommentsById", notes = "Fetch Case Comments based on CaseID", tags = {
			"crm", })
	@RequestMapping(method = RequestMethod.GET, value = "/caseComments/{id}", produces = { "application/json" })
	public ResponseEntity<Object> fetchCaseCommentsById(@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.fetchCaseCommentsById(id));
	}

	@ApiOperation(value = "", nickname = "createComment", notes = "Update A new Comment", tags = { "crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/createComment", produces = { "application/json" })
	public ResponseEntity<Object> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest)
			throws Exception {
		return ResponseEntity.ok(crmService.addCommentToCase(createCommentRequest));
	}

	@ApiOperation(value = "", nickname = "fetchCaseDocsByCaseId", notes = "Fetch Case Docs based on CaseID", tags = {
			"crm", })
	@RequestMapping(method = RequestMethod.GET, value = "/caseDocs/{caseId}", produces = { "application/json" })
	public ResponseEntity<Object> fetchCaseDocsByCaseId(@PathVariable @NotNull String caseId) throws Exception {
		return ResponseEntity.ok(crmService.fetchDocListForCase(caseId));
	}

	@ApiOperation(value = "", nickname = "fetchCaseDocContentById", notes = "Fetch Case Doc Content based on ID", tags = {
			"crm", })
	@RequestMapping(method = RequestMethod.GET, value = "/caseDocContent/{id}", produces = { "application/json" })
	public ResponseEntity<Object> fetchCaseDocContentById(@PathVariable @NotNull String id) throws Exception {
		return ResponseEntity.ok(crmService.fetchCaseDocContentById(id));
	}

	@ApiOperation(value = "", nickname = "addDocToCase", notes = "Add New Doc to CaseID", tags = { "crm", })
	@RequestMapping(method = RequestMethod.POST, value = "/addDoc/{caseId}", produces = { "application/json" })
	public ResponseEntity<Object> addDocToCase(@Valid @RequestBody Document docRequest,
			@PathVariable @NotNull String caseId) throws Exception {
		return ResponseEntity.ok(crmService.addDocToCase(caseId, docRequest));
	}

	@ApiOperation(value = "", nickname = "deleteCase", notes = "Deletes a Case By Id", tags = { "crm", })
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteCase/{caseId}", produces = { "application/json" })
	public ResponseEntity<Object> deleteCase(@PathVariable @NotNull String caseId) throws Exception {
		return ResponseEntity.ok(crmService.deleteCase(caseId));
	}

	@ApiOperation(value = "", nickname = "deleteComment", notes = "Deletes a Comment By Id", tags = { "crm", })
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteComment/{commentId}", produces = {
			"application/json" })
	public ResponseEntity<Object> deleteComment(@PathVariable @NotNull String commentId) throws Exception {
		return ResponseEntity.ok(crmService.deleteComment(commentId));
	}
	
	@ApiOperation(value = "", nickname = "deleteDocumentById", notes = "Deletes a Document By Id", tags = { "crm", })
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteDocument/{docId}", produces = {
			"application/json" })
	public ResponseEntity<Object> deleteDocumentById(@PathVariable @NotNull String docId) throws Exception {
		return ResponseEntity.ok(crmService.deleteDocumentById(docId));
	}
}
