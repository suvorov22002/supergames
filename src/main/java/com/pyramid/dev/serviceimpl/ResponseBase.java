package com.pyramid.dev.serviceimpl;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.responsecode.ResponseHolder;

public class ResponseBase implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	

	private String code; 

	private String error;
	
	private String message;
	
	/**
	 * @param code
	 * @param error
	 */
	public ResponseBase(String code, String error) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setError(ResponseHolder.mapMessage.get(this.getCode()));
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.SUCESS)) return Boolean.TRUE;
		else return Boolean.FALSE;
	}
	
	public ResponseBase error(String code) {
		this.setCode(code);
		this.setError(ResponseHolder.mapMessage.get(code));
		return this;
	}

	/**
	 * 
	 */
	public ResponseBase() {
		super();
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
		//System.out.println("---------ResponseBase----------"+code);
		this.code = code;
		if(ResponseHolder.mapMessage.get(this.code) != null) {
			this.code = code;
			this.setMessage(ResponseHolder.mapMessage.get(this.code));
			//this.setError(ResponseHolder.mapMessage.get(this.code));
		}else {
			
			this.setMessage(ResponseHolder.mapMessage.get(this.code));
			code = "503";
			this.code = code;
			this.setError(ResponseHolder.mapMessage.get(this.code));
		}
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

}
