package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.KenoRes;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class KenoResDTO extends ResponseBase {
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private KenoRes kres;
	
	

	public KenoResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KenoResDTO(String code, String error, String message, KenoRes kres) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.kres = kres;
	}

	public KenoRes getKres() {
		return kres;
	}

	public void setKres(KenoRes kres) {
		this.kres = kres;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static KenoResDTO getInstance(){
		return new KenoResDTO();
	}

	public KenoResDTO event(KenoRes kres) {
		this.setKres(kres);
		return this;
	}
	
	public KenoResDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public KenoResDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	
	
}
