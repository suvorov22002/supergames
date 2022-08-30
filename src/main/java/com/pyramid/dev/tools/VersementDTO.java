package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.Versement;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class VersementDTO extends ResponseBase{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Versement vers;

	public VersementDTO() {
		super();
	}
	
	public VersementDTO(String code, String error, String message, Versement vers) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.vers = vers;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static VersementDTO getInstance(){
		return new VersementDTO();
	}
	
	public VersementDTO event(Versement btick) {
		this.setVers(btick);
		return this;
	}
	
	public VersementDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public VersementDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public Versement getVers() {
		return vers;
	}

	public void setVers(Versement vers) {
		this.vers = vers;
	}
	
}
