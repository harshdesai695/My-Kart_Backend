package com.myKart.infra.exception;

public class BussinessException extends Exception{
	public BussinessException(String errorMessage) {
		super(errorMessage);
	}
}
