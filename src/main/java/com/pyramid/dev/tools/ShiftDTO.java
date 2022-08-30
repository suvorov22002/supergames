package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.ShiftDay;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class ShiftDTO extends ResponseBase{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private ShiftDay shift;

	public ShiftDTO() {
		super();
	}

	public ShiftDTO(String code, String error, String message, ShiftDay shift) {
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.shift = shift;
	}

	public ShiftDay getShift() {
		return shift;
	}

	public void setShift(ShiftDay shift) {
		this.shift = shift;
	}
	
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}
	
	public static ShiftDTO getInstance(){
		return new ShiftDTO();
	}
	
	public ShiftDTO event(ShiftDay shift) {
		this.setShift(shift);
		return this;
	}
	
	public ShiftDTO error(String code) {
		this.setCode(code);
		return this;
	}
	
	public ShiftDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		this.setError(msg);
		return this;
	}
}
