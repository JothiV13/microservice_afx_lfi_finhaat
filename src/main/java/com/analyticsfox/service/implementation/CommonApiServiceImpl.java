package com.analyticsfox.service.implementation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.analyticsfox.constants.Constants;
import com.analyticsfox.constants.ErrorConstants;
import com.analyticsfox.service.ICommonApiService;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CommonApiServiceImpl implements ICommonApiService {

    @Override
    public void commonSuccessResponse(JSONObject jsonResponse) {
        jsonResponse.put(Constants.STATUSCODE, Constants.SUCCESSCD);
        jsonResponse.put(Constants.ERROR, Boolean.FALSE);
    }

    @Override
    public void commonErrorResponse(JSONObject jsonResponse) {
        jsonResponse.put(Constants.STATUSCODE, Constants.SUCCESSCD);
        jsonResponse.put(Constants.ERROR, Boolean.TRUE);
    }

    @Override
    public String commonExceptionResponse(JSONObject jsonResponse, Exception exception) {
        String responseMessage;
        jsonResponse.put(Constants.STATUSCODE, Constants.ERRORCD);
        jsonResponse.put(Constants.MESSAGE, ErrorConstants.TECHNICAL_ERROR);
        jsonResponse.put(Constants.ERROR, Boolean.TRUE);
        jsonResponse.put(Constants.DATA, Constants.EMPTYDATA);
        responseMessage = jsonResponse.toString();
        return responseMessage;
    }


    public ResponseEntity<String> sendAPI(String apiURL, HttpEntity<String> request) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiURL, request, String.class);
        return response;
    }

    @Override
    public ResponseEntity<String> sendMultipartAPI(String apiURL, HttpEntity<MultiValueMap<String, Object>> request) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiURL, request, String.class);
        return response;
    }



    @Override
    public JSONObject processResponseByStatus(HttpStatus statusCode, JSONObject jsonResponseBody) {
        JSONObject response = new JSONObject();
        response.put("status", statusCode);
        switch (statusCode) {
            case OK: if (jsonResponseBody.getInt("statusCode") == HttpStatus.SWITCHING_PROTOCOLS.value()) {
                JSONObject object = (JSONObject) jsonResponseBody.get("result");
                object.put("requestId", jsonResponseBody.has("requestId") ? jsonResponseBody.getString("requestId") : "");
                if (object.has("responseStatusCode")) {
                    object.put("message", "OTP Entered is Invalid");
                }
                response.put("data", object);
            } else if (jsonResponseBody.getInt("statusCode") == HttpStatus.PROCESSING.value()) {
                JSONObject object = new JSONObject(jsonResponseBody.get("result"));
                object.put("requestId", jsonResponseBody.getString("requestId"));
                object.put("message", "OTP has been expired");
                response.put("data", object);
            }
                break;

            default:
                JSONObject object = new JSONObject(jsonResponseBody.get("result"));
                object.put("requestId", jsonResponseBody.getString("requestId"));
                response.put("data", object);
                break;
        }
        return response;
    }

    public JSONObject processResponseByStatusForKycOcr(HttpStatus statusCode, JSONObject jsonResponseBody) {
        JSONObject response = new JSONObject();
        response.put("status", statusCode);
        switch (statusCode) {
            case OK: if (jsonResponseBody.getInt("statusCode") == HttpStatus.SWITCHING_PROTOCOLS.value()) {
                JSONArray array=(JSONArray) jsonResponseBody.get("result");
                JSONObject object=new JSONObject();
                object.put("result",array);
                object.put("requestId", jsonResponseBody.has("requestId") ? jsonResponseBody.getString("requestId") : "");
                object.put("statusCode", jsonResponseBody.has("statusCode") ? jsonResponseBody.getLong("statusCode") : "");
                if (object.has("responseStatusCode")) {
                    object.put("message", "Invalid");
                }
                response.put("data", object);
            } else if (jsonResponseBody.getInt("statusCode") == HttpStatus.PROCESSING.value()) {
                JSONObject object = new JSONObject(jsonResponseBody.get("result"));
                object.put("requestId", jsonResponseBody.getString("requestId"));
                object.put("message", "Data stored");
                response.put("data", object);
            }
                break;

            default:
                JSONObject object = new JSONObject(jsonResponseBody.get("result"));
                object.put("requestId", jsonResponseBody.getString("requestId"));
                response.put("data", object);
                break;
        }
        return response;
    }

	@Override
	public void commonSuccessErrorTrueResponse(JSONObject jsonResponse) {
		jsonResponse.put("statusCode", "200");
		jsonResponse.put("error", Boolean.TRUE);
	}

	
    
}
