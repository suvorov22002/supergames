package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.CaissierDto;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class CaissierDTO extends ResponseBase {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private CaissierDto cais;

	public CaissierDTO() {
		super();
	}

	public CaissierDTO(String code, String error, String message, CaissierDto cais) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.cais = cais;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static CaissierDTO getInstance(){
		return new CaissierDTO();
	}

	public CaissierDTO event(CaissierDto cais) {
		this.setCais(cais);
		return this;
	}
	
	public CaissierDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public CaissierDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public CaissierDto getCais() {
		return cais;
	}

	public void setCais(CaissierDto cais) {
		this.cais = cais;
	}
	
	

}
