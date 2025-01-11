package com.myKart.infra.dto;

public class RequestWrapper {
	private Object requestBody;
	private Object context;
	public Object getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}
	public Object getContext() {
		return context;
	}
	public void setContext(Object context) {
		this.context = context;
	}
	@Override
	public String toString() {
		return "RequestWrapper [requestBody=" + requestBody + ", context=" + context + "]";
	}
}
     
