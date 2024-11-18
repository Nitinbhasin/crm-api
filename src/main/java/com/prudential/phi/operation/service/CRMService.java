package com.prudential.phi.operation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.phi.operation.models.CreateCaseRequest;
import com.prudential.phi.operation.models.CreateCommentRequest;
import com.prudential.phi.operation.models.Document;
import com.prudential.phi.operation.models.FilterExpression;
import com.prudential.phi.operation.models.LogicalExpression;
import com.prudential.phi.operation.models.Query;
import com.prudential.phi.operation.models.QueryOrderBy;
import com.prudential.phi.operation.models.SearchCasesResponse;
import com.prudential.phi.operation.models.SimpleExpression;
import com.prudential.phi.operation.models.UpdateCaseRequest;
import com.prudential.phi.operation.models.UpdateCommentRequest;
import com.prudential.phi.operation.util.RestUtil;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CRMService {

	@Autowired
	private RestUtil restUtil;

	private String instanceUrl;

	@Value("${crm.loginUrl}")
	private String CRM_LOGIN_URL;

	@Value("${crm.username}")
	private String CRM_USERNAME;

	@Value("${crm.password}")
	private String CRM_PASSWORD;

	@Value("${crm.clientId}")
	private String CRM_CLIENT_ID;

	@Value("${crm.clientSecret}")
	private String CRM_CLIENT_SECRET;
	
	public String createSalesForceCase(CreateCaseRequest createCaseRequest) throws Exception {
		String accessToken = doLogin();
		String caseId = registerCase(accessToken, createCaseRequest);
		log.info("Case Created With Id: {}", caseId);
		String caseNumber = fetchCaseNumber(accessToken, caseId);
		log.info("Case Created With Number: {}", caseNumber);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("caseNumber", caseNumber);
		if (!CollectionUtils.isEmpty(createCaseRequest.getDocuments())) {
			uploadDocuments(createCaseRequest.getDocuments(), accessToken, caseId);
		}
		return jsonObject.toString();
	}

	private void uploadDocuments(List<Document> documents, String accessToken, String caseId) throws Exception {
		for (Document document : documents) {
			String docId = uploadContent(accessToken, document, caseId);
			log.info("Doc Created With Id: {}", docId);
			String contentDocId = fetchContentDocumentId(accessToken, docId);
			log.info("Doc Created With Content Id: {}", contentDocId);
			attachDocToCase(accessToken, caseId, contentDocId);
		}

	}

	public String uploadContent(String accessToken, Document document, String caseId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Title", "Attachment for Case " + caseId);
		jsonObject.put("PathOnClient", document.getName());
		jsonObject.put("ContentLocation", "S");
		jsonObject.put("VersionData", document.getContent());

		String body = restUtil.sendPost(instanceUrl, accessToken, jsonObject.toString(),
				"services/data/v62.0/sobjects/ContentVersion", MediaType.APPLICATION_JSON);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.getString("id");
	}

	public String addCommentToCase(CreateCommentRequest createCommentRequest) throws Exception {
		String access_token = doLogin();
		if(IsClosedCase(createCommentRequest.getCaseId(), access_token))
		{
			throw new InternalError("Case is Already Closed");
		}
		JSONObject jsonObject = new JSONObject();
		String commentId = addComment(access_token, createCommentRequest.getBody(), createCommentRequest.getCaseId(),
				createCommentRequest.getIsPublic());
		if (!StringUtils.isEmpty(commentId)) {
			log.info("Comment is added to case with comment id: "+ commentId);
			jsonObject.put("success", true);
			jsonObject.put("message", "Added Successfully");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "Updated Successfully");
		}
		return jsonObject.toString();
	}

	public String addComment(String accessToken, String commentBody, String caseId, Boolean isPublic) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ParentId", caseId);
		jsonObject.put("CommentBody", commentBody);
		jsonObject.put("IsPublished", isPublic);
		String body = restUtil.sendPost(instanceUrl, accessToken, jsonObject.toString(),
				"services/data/v62.0/sobjects/CaseComment", MediaType.APPLICATION_JSON);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.getString("id");
	}

	public String fetchContentDocumentId(String accessToken, String docId) throws Exception {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String query = "Select+ContentDocumentId+FROM+ContentVersion+WHERE+Id+=+'" + docId + "'";
		queryParams.add("q", query);
		String body = restUtil.sendGET(instanceUrl, "services/datav62.0/query", accessToken, queryParams);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.getJSONArray("records").getJSONObject(0).getString("ContentDocumentId");
	}

	public void attachDocToCase(String accessToken, String caseId, String contentDocId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ContentDocumentId", contentDocId);
		jsonObject.put("LinkedEntityId", caseId);
		jsonObject.put("Visibility", "AllUsers");

		restUtil.sendPost(instanceUrl, accessToken, jsonObject.toString(),
				"services/datav62.0/sobjects/ContentDocumentLink", MediaType.APPLICATION_JSON);
	}

	public String doLogin() throws Exception {
		List<NameValuePair> parametersBody = new ArrayList<NameValuePair>();
		parametersBody.add(new BasicNameValuePair("grant_type", "password"));
		parametersBody.add(new BasicNameValuePair("client_id", CRM_CLIENT_ID));
		parametersBody.add(new BasicNameValuePair("client_secret", CRM_CLIENT_SECRET));
		parametersBody.add(new BasicNameValuePair("username", CRM_USERNAME));
		parametersBody.add(new BasicNameValuePair("password", CRM_PASSWORD));
		String body = restUtil.sendPost(CRM_LOGIN_URL, parametersBody, "services/oauth2/token",
				MediaType.APPLICATION_FORM_URLENCODED);
		JSONObject jsonObject = new JSONObject(body);
		instanceUrl = jsonObject.getString("instance_url");
		return jsonObject.getString("access_token");

	}

	public String fetchCaseCommentsById(String caseId) throws Exception {
		String access_token = doLogin();
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/sobjects/Case/" + caseId + "/CaseComments",
				access_token, queryParams);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.toString();
	}

	public String fetchCaseHistoryById(String docId) throws Exception {
		String access_token = doLogin();
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String query = "Select+FIELDS(All)+FROM+CASEHISTORY+WHERE+CaseId+=+'" + docId
				+ "'+ORDER+BY+CreatedDate+DESC+LIMIT+100";
		queryParams.add("q", query);
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/query", access_token, queryParams);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.toString();
	}

	public String registerCase(String accessToken, CreateCaseRequest createCaseRequest) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Origin", createCaseRequest.getOrigin());
		jsonObject.put("SuppliedName", createCaseRequest.getName());
		jsonObject.put("SuppliedPhone", createCaseRequest.getPhone());
		jsonObject.put("Subject", createCaseRequest.getSubject());
		jsonObject.put("SuppliedEmail", createCaseRequest.getEmail());
		jsonObject.put("Description", createCaseRequest.getDescription());
		String body = restUtil.sendPost(instanceUrl, accessToken, jsonObject.toString(),
				"services/data/v62.0/sobjects/Case", MediaType.APPLICATION_JSON);
		JSONObject responseObject = new JSONObject(body);
		String caseId= responseObject.getString("id");
		log.info("A new case is registered with case Id: "+ caseId);
		return caseId;
	}

	public String fetchCaseNumber(String accessToken, String caseId) throws Exception {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/sobjects/Case/" + caseId, accessToken,
				queryParams);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.getString("CaseNumber");
	}

	public Boolean IsClosedCase(String caseId, String accessToken) throws Exception {

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/sobjects/Case/" + caseId, accessToken,
				queryParams);
		JSONObject obj = new JSONObject(body);
		return obj.optBoolean("IsClosed");
	}
	
	public SearchCasesResponse fetchCaseByID(String caseId) throws Exception {
		String access_token = doLogin();
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/sobjects/Case/" + caseId, access_token,
				queryParams);
		JSONObject obj = new JSONObject(body);
		SearchCasesResponse caseDetails = new SearchCasesResponse();
		caseDetails.setName(obj.optString("SuppliedName").replace("null", ""));
		caseDetails.setCaseId(obj.optString("Id").replace("null", ""));
		caseDetails.setCaseNumber(obj.optString("CaseNumber").replace("null", ""));
		caseDetails.setClosedDate(obj.optString("ClosedDate").replace("null", ""));
		caseDetails.setCreatedDate(obj.optString("CreatedDate").replace("null", ""));
		caseDetails.setDescription(obj.optString("Description").replace("null", ""));
		caseDetails.setEmail(obj.optString("SuppliedEmail").replace("null", ""));
		caseDetails.setIsClosed(obj.optBoolean("IsClosed"));
		caseDetails.setLastModifiedDate(obj.optString("LastModifiedDate").replace("null", ""));
		caseDetails.setOrigin(obj.optString("Origin").replace("null", ""));
		caseDetails.setPhone(obj.optString("SuppliedPhone").replace("null", ""));
		caseDetails.setStatus(obj.optString("Status").replace("null", ""));
		caseDetails.setSubject(obj.optString("Subject").replace("null", ""));
		caseDetails.setOwnerId(obj.optString("OwnerId").replace("null", ""));
		return caseDetails;
	}

	public List<SearchCasesResponse> fetchCasesByQuery(Query query) throws Exception {
		String access_token = doLogin();
		List<SearchCasesResponse> caseList = new ArrayList<>();
		JSONObject response = queryByCriteria(query, access_token);
		if (response.getJSONArray("records") != null && response.getJSONArray("records").length() > 0) {
			for (int i = 0; i < response.getJSONArray("records").length(); i++) {
				JSONObject obj = response.getJSONArray("records").getJSONObject(i);
				if (obj != null) {
					SearchCasesResponse caseDetails = new SearchCasesResponse();
					caseDetails.setName(obj.optString("SuppliedName").replace("null", ""));
					caseDetails.setCaseId(obj.optString("Id").replace("null", ""));
					caseDetails.setCaseNumber(obj.optString("CaseNumber").replace("null", ""));
					caseDetails.setClosedDate(obj.optString("ClosedDate").replace("null", ""));
					caseDetails.setCreatedDate(obj.optString("CreatedDate").replace("null", ""));
					caseDetails.setDescription(obj.optString("Description").replace("null", ""));
					caseDetails.setEmail(obj.optString("SuppliedEmail").replace("null", ""));
					caseDetails.setIsClosed(obj.optBoolean("IsClosed"));
					caseDetails.setLastModifiedDate(obj.optString("LastModifiedDate").replace("null", ""));
					caseDetails.setOrigin(obj.optString("Origin").replace("null", ""));
					caseDetails.setPhone(obj.optString("SuppliedPhone").replace("null", ""));
					caseDetails.setStatus(obj.optString("Status").replace("null", ""));
					caseDetails.setSubject(obj.optString("Subject").replace("null", ""));
					caseDetails.setOwnerId(obj.optString("OwnerId").replace("null", ""));
					caseList.add(caseDetails);
				}
			}
		}

		return caseList;
	}

	public String updateCase(String caseId, UpdateCaseRequest updateCaseRequest) throws Exception {
		String access_token = doLogin();
		if(IsClosedCase(caseId, access_token))
		{
			throw new InternalError("Case is Already Closed");
		}
		JSONObject jsonObject = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
		String jsonBody = objectMapper.writeValueAsString(updateCaseRequest);
		Integer statusCode = restUtil.sendPatch(instanceUrl, access_token, jsonBody,
				"services/data/v62.0/sobjects/Case/" + caseId, MediaType.APPLICATION_JSON);
		if (statusCode == 204) {
			jsonObject.put("isUpdated", true);
			jsonObject.put("message", "Updated Successfully");
		} else
			throw new InternalError("Failed To Update");

		return jsonObject.toString();
	}
	
	public String updateComment(String commentId, UpdateCommentRequest updateCommentRequest) throws Exception {
		String access_token = doLogin();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("CommentBody", updateCommentRequest.getBody());
		jsonObject.put("IsPublished", updateCommentRequest.getIsPublic());
		Integer statusCode = restUtil.sendPatch(instanceUrl, access_token, jsonObject.toString(),
				"services/data/v62.0/sobjects/CaseComment/" + commentId, MediaType.APPLICATION_JSON);
		
		if (statusCode == 204) {
			jsonObject= new JSONObject();
			jsonObject.put("isUpdated", true);
			jsonObject.put("message", "Updated Successfully");
		} else
			throw new InternalError("Failed To Update");

		return jsonObject.toString();
	}
	
	public String deleteCase(String caseId) throws Exception {
		String access_token = doLogin();
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();

		Integer statusCode = restUtil.sendDELETE(instanceUrl, "services/data/v62.0/sobjects/Case/" + caseId,
				access_token, queryParams);
		JSONObject jsonObject = new JSONObject();
		if (statusCode == 204) {
			log.info("Case is deleted with caseID: "+caseId);
			jsonObject.put("isDeleted", true);
			jsonObject.put("message", "Deleted Successfully");
		} else
			throw new InternalError("Failed To Delete");

		return jsonObject.toString();
	}
	
	public String deleteComment(String commentId) throws Exception {
		String access_token = doLogin();		
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();

		Integer statusCode = restUtil.sendDELETE(instanceUrl, "services/data/v62.0/sobjects/CaseComment/" + commentId,
				access_token, queryParams);
		JSONObject jsonObject = new JSONObject();
		if (statusCode == 204) {
			log.info("Comment is deleted with caseID: "+commentId);
			jsonObject.put("isDeleted", true);
			jsonObject.put("message", "Deleted Successfully");
		} else
			throw new InternalError("Failed To Delete");

		return jsonObject.toString();
	}
	
	public String deleteDocumentById(String docId) throws Exception {
		String access_token = doLogin();
		String contentDocId=fetchContentDocumentId(access_token, docId);
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();

		Integer statusCode = restUtil.sendDELETE(instanceUrl, "services/data/v62.0/sobjects/ContentDocument/" + contentDocId,
				access_token, queryParams);
		JSONObject jsonObject = new JSONObject();
		if (statusCode == 204) {
			log.info("Document is deleted with docId: "+docId);
			jsonObject.put("isDeleted", true);
			jsonObject.put("message", "Deleted Successfully");
		} else
			throw new InternalError("Failed To Delete");

		return jsonObject.toString();
	}

	public String addDocToCase(String caseId, Document document) throws Exception {
		String access_token = doLogin();
		if(IsClosedCase(caseId, access_token))
		{
			throw new InternalError("Case is Already Closed");
		}
		String docId = uploadContent(access_token, document, caseId);
		log.info("Doc Created With Id: {}", docId);
		String contentDocId = fetchContentDocumentId(access_token, docId);
		log.info("Doc Created With Content ID: {}", contentDocId);
		attachDocToCase(access_token, caseId, contentDocId);
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("success", true);
		jsonObject.put("message", "Added Successfully");

		return jsonObject.toString();
	}

	public JSONObject queryByCriteria(Query query, String access_token) throws Exception {
		String objectClassName = query.getObjectClassName();
		String projs = "FIELDS(ALL)";
		String orderBy = "";
		if (!CollectionUtils.isEmpty(query.getProjs())) {
			projs = String.join(" , ", query.getProjs());
		}
		List<String> conditions = new ArrayList<>();
		List<String> orderBys = new ArrayList<>();

		if (query.getOrderBy() != null) {
			for (QueryOrderBy exp : query.getOrderBy()) {
				orderBys.add(exp.getProp() + " " + exp.getOrder());
			}
		}

		LogicalExpression logicExpression = query.getFilter().getLogicalExpression();
		for (FilterExpression exp : logicExpression.getExpressions()) {
			SimpleExpression expression = (SimpleExpression) exp.getSimpleExpression();
			conditions.add(expression.getLhs().get(0) + " " + expression.getOp() + " '"
					+ expression.getValue().getValue() + "' ");
		}
		String condition = String.join(" " + logicExpression.getOp().name() + " ", conditions);
		if (query.getOrderBy() != null)
			orderBy = String.join(" , ", orderBys);
		int maxHits = 100;
		if (query.getLimit() != null && query.getLimit() > 0)
			maxHits = query.getLimit();
		StringBuilder searchQuery = new StringBuilder();
		searchQuery.append("SELECT " + projs + " FROM " + objectClassName + " WHERE " + condition);
		if (!orderBy.isEmpty())
			searchQuery.append(" ORDER BY " + orderBy);
		searchQuery.append(" LIMIT " + maxHits);
		String queryWithPlus = searchQuery.toString().replace(" ", "+");
		log.info("Search Query Is: " + queryWithPlus);
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		queryParams.add("q", queryWithPlus);
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/query", access_token, queryParams);
		JSONObject responseObject = new JSONObject(body);
		System.out.println(responseObject.toString());
		return responseObject;
	}

	public String fetchCaseDocContentById(String docId) throws Exception {
		String access_token = doLogin();
		String body = restUtil.getOctetStream(
				instanceUrl + "/services/data/v62.0/sobjects/ContentVersion/" + docId + "/VersionData", access_token);
		JSONObject obj = new JSONObject();
		obj.put("content", body);
		return obj.toString();

	}

	public String fetchDocListForCase(String caseId) throws Exception {
		String access_token = doLogin();
		String ids = fetchDocumentIdsForCase(access_token, caseId);
		if (ids == null) {
			return new JSONObject().toString();
		}
		return fetchContentVersionIdsForDocList(access_token, ids).toString();
	}

	public String fetchDocumentIdsForCase(String accessToken, String caseId) throws Exception {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String query = "Select+Id,LinkedEntityId,ContentDocumentId+FROM+ContentDocumentLink+WHERE+LinkedEntityId+=+'"
				+ caseId + "'";
		queryParams.add("q", query);
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/query", accessToken, queryParams);
		JSONObject responseObject = new JSONObject(body);
		StringBuilder ids = new StringBuilder();
		if (responseObject.getJSONArray("records").length() == 0) {
			return null;
		}
		ids.append("(");
		for (int i = 0; i < responseObject.getJSONArray("records").length(); i++) {
			ids.append("'")
					.append(responseObject.getJSONArray("records").getJSONObject(i).optString("ContentDocumentId"))
					.append("'");
			if (i < responseObject.getJSONArray("records").length() - 1) {
				ids.append(",");
			}
		}
		ids.append(")");
		return ids.toString();
	}

	public String fetchContentVersionIdsForDocList(String accessToken, String contentDocumentIdList) throws Exception {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		String query = "Select+Id,Title,Description,PathOnClient,VersionData,CreatedDate+FROM+ContentVersion+WHERE+ContentDocumentId+IN+"
				+ contentDocumentIdList + "+AND+IsLatest+=+true+LIMIT+100";
		queryParams.add("q", query);
		String body = restUtil.sendGET(instanceUrl, "services/data/v62.0/query", accessToken, queryParams);
		JSONObject responseObject = new JSONObject(body);
		return responseObject.toString();
	}

}
