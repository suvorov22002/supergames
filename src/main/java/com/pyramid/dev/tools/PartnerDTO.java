package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class PartnerDTO extends ResponseBase {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Partner part;

	public PartnerDTO() {
		super();
	}

	public PartnerDTO(String code, String error, String message, Partner part) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.part = part;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static PartnerDTO getInstance(){
		return new PartnerDTO();
	}

	public PartnerDTO event(Partner part) {
		this.setPart(part);
		return this;
	}
	
	public PartnerDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public PartnerDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public Partner getPart() {
		return part;
	}

	public void setPart(Partner part) {
		this.part = part;
	}
	
}
