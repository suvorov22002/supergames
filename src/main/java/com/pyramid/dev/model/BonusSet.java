package com.pyramid.dev.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BonusSet implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private int bonusk;
	private int code;
	private double montant;
	private String coderace;
	private Long barcode;
	private Long mise;
	private int numk;
	
	public BonusSet() {
		super();
	}

	public int getBonusk() {
		return bonusk;
	}

	public void setBonusk(int bonusk) {
		this.bonusk = bonusk;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public Long getBarcode() {
		return barcode;
	}

	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}

	public Long getMise() {
		return mise;
	}

	public void setMise(Long mise) {
		this.mise = mise;
	}

	public int getNumk() {
		return numk;
	}

	public void setNumk(int numk) {
		this.numk = numk;
	}
	
	
	
}
