package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.BonusSet;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class BonusSetDTO extends ResponseBase{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private BonusSet bnset;

	public BonusSetDTO() {
		super();
	}

	public BonusSetDTO(String code, String error, String message, BonusSet bnset) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.bnset = bnset;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static BonusSetDTO getInstance(){
		return new BonusSetDTO();
	}
	
	public BonusSetDTO event(BonusSet bnset) {
		this.setBnset(bnset);
		return this;
	}
	
	public BonusSetDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public BonusSetDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}

	public BonusSet getBnset() {
		return bnset;
	}

	public void setBnset(BonusSet bnset) {
		this.bnset = bnset;
	}
	
}
