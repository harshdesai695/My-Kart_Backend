package com.myKart.infra.dto;

import org.springframework.http.HttpHeaders;



public class ResponseWrapper {
	private Object responseBody;
	private int responseStatus;
	private HttpHeaders httpHeaders;
	
	public ResponseWrapper() {}
	
	public ResponseWrapper(Object responseBody, int responseStatus, HttpHeaders httpHeaders) {
		super();
		this.responseBody = responseBody;
		this.responseStatus = responseStatus;
		this.httpHeaders = httpHeaders;
	}
	
	
	public Object getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}
	public int getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	@Override
	public String toString() {
		return "ResponseWrapper [responseBody=" + responseBody + ", responseStatus=" + responseStatus + ", httpHeaders="
				+ httpHeaders + "]";
	}
	
	
}
