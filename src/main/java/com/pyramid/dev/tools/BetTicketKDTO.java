package com.pyramid.dev.tools;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.model.BetTicketK;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.serviceimpl.ResponseBase;

public class BetTicketKDTO extends ResponseBase {

	/**
	 *
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private BetTicketK btick;

	public BetTicketKDTO() {
		super();
	}

	public BetTicketKDTO(String code, String error, String message, BetTicketK btick) {
		super();
		this.setCode(code);
		this.setError(error);
		this.setMessage(message);
		this.btick = btick;
	}

	@Override
	public Boolean status() {
		if(StringUtils.equalsIgnoreCase(this.getCode(), ResponseHolder.FAIL)) return Boolean.FALSE;
		else return Boolean.TRUE;
	}

	public static BetTicketKDTO getInstance(){
		return new BetTicketKDTO();
	}

	public BetTicketKDTO event(BetTicketK btick) {
		this.setBtick(btick);
		return this;
	}

	@Override
	public BetTicketKDTO error(String code) {
		this.setCode(code);
		return this;
	}

	public BetTicketKDTO sucess(String msg) {
		this.setCode(ResponseHolder.SUCESS);
		//this.setError(msg);
		this.setMessage(msg);
		return this;
	}

	public BetTicketK getBtick() {
		return btick;
	}

	public void setBtick(BetTicketK btick) {
		this.btick = btick;
	}

}
