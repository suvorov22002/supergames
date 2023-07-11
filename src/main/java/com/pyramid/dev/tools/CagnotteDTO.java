package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.CagnotteDto;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class CagnotteDTO  extends ResponseBase{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private CagnotteDto cagnot;
	
	public CagnotteDTO() {
		super();
	}

	public CagnotteDTO(String code, String error, String message, CagnotteDto cagnot) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.cagnot = cagnot;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static CagnotteDTO getInstance(){
		return new CagnotteDTO();
	}

	public CagnotteDTO event(CagnotteDto ca) {
		this.setCagnot(ca);
		return this;
	}
	
	public CagnotteDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public CagnotteDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public CagnotteDto getCagnot() {
		return cagnot;
	}

	public void setCagnot(CagnotteDto cagnot) {
		this.cagnot = cagnot;
	}

}
