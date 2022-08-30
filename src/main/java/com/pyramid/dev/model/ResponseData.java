package com.pyramid.dev.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.responsecode.ResponseHolder;

public class ResponseData implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	private String code; 

	private String error;
	
	private String message;

	private String data;

	/**
	 * 
	 */
	public ResponseData() {
		super();
	}

	/**
	 * @param code
	 * @param error
	 * @param message
	 * @param eve
	 */
	public ResponseData(String code, String error, String message, String data) {
		super();
		this.code = code;
		this.error = error;
		this.message = message;
		this.data = data;
	}

	public static ResponseData getInstance(){
		return new ResponseData();
	}
	
//	public ResponseData(BkeveDtoResponce dto){
//		super();
//		this.code = dto.getCode();
//		this.error = dto.getError();
//		this.message = dto.getMessage();
//		JSONArray json = new JSONArray();
//		JSONObject obj = new JSONObject(dto.getEve01()); 
//		json.put(obj);
//		obj = new JSONObject(dto.getEve02()); 
//		json.put(obj);
//		this.data = json.toString();
//	}
	

	public ResponseData event(String data) {
		this.setData(data);
		return this;
	}
	
	public ResponseData error(String msg) {
		this.setCode(ResponseHolder.FAIL);
		this.setMessage("FAIL");
		this.setError(msg);
		this.setError(ResponseHolder.mapMessage.get(this.code));
		return this;
	}
	
	public ResponseData errorCode(String code) {
		this.setCode(code);
		this.setMessage("FAIL");
		this.setError(ResponseHolder.mapMessage.get(this.code));
		return this;
	}
	
	public ResponseData sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setMessage("SUCCESS");
		this.setError(msg);
		this.setMessage(ResponseHolder.mapMessage.get(this.code));
		this.setError(ResponseHolder.mapMessage.get(this.code));
		return this;
	}
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
}
