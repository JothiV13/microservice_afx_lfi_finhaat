package com.analyticsfox.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface ICommonApiService {

	void commonErrorResponse(JSONObject jsonResponse);

	void commonSuccessResponse(JSONObject jsonResponse);

	String commonExceptionResponse(JSONObject jsonResponse, Exception exception);

	ResponseEntity<String> sendAPI(String apiURL, HttpEntity<String> request);

	ResponseEntity<String> sendMultipartAPI(String apiURL, HttpEntity<MultiValueMap<String, Object>> request);

	JSONObject processResponseByStatus(HttpStatus statusCode, JSONObject jsonObject);

	public JSONObject processResponseByStatusForKycOcr(HttpStatus statusCode, JSONObject jsonResponseBody);
	
	public void commonSuccessErrorTrueResponse(JSONObject jsonResponse);
}
