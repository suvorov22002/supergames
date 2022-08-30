package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class KenoDTO extends ResponseBase {
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Keno ken;

	public KenoDTO() {
		super();
	}

	public KenoDTO(String code, String error, String message, Keno ken) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.ken = ken;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static KenoDTO getInstance(){
		return new KenoDTO();
	}

	public KenoDTO event(Keno ken) {
		this.setKen(ken);
		return this;
	}
	
	public KenoDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public KenoDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public Keno getKen() {
		return ken;
	}

	public void setKen(Keno ken) {
		this.ken = ken;
	}
	
	

}
