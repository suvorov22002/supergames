package com.pyramid.dev.model;

import java.io.Serializable;

public class PartnerDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idpartner;
	private String coderace;
	private String zone;
	private double bonusKamount;
	private double bonusBamount;
	private double bonusRamount;
	private double bonusDamount;
	private double bonusPamount;
	private double bnskmin;
	private double bnskmax;
	private double bnspmin;
	private double bnspmax;
	private double bnsdmin;
	private double bnsdmax;
	private double bnsbmin;
	private double bnsbmax;

	private int actif;
	
	public PartnerDto() {
		super();
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public double getBonusKamount() {
		return bonusKamount;
	}

	public void setBonusKamount(double bonusKamount) {
		this.bonusKamount = bonusKamount;
	}

	public double getBonusBamount() {
		return bonusBamount;
	}

	public void setBonusBamount(double bonusBamount) {
		this.bonusBamount = bonusBamount;
	}

	public double getBonusRamount() {
		return bonusRamount;
	}

	public void setBonusRamount(double bonusRamount) {
		this.bonusRamount = bonusRamount;
	}

	public double getBonusDamount() {
		return bonusDamount;
	}

	public void setBonusDamount(double bonusDamount) {
		this.bonusDamount = bonusDamount;
	}

	public double getBonusPamount() {
		return bonusPamount;
	}

	public void setBonusPamount(double bonusPamount) {
		this.bonusPamount = bonusPamount;
	}

	public double getBnskmin() {
		return bnskmin;
	}

	public void setBnskmin(double bnskmin) {
		this.bnskmin = bnskmin;
	}

	public double getBnskmax() {
		return bnskmax;
	}

	public void setBnskmax(double bnskmax) {
		this.bnskmax = bnskmax;
	}

	public double getBnspmin() {
		return bnspmin;
	}

	public void setBnspmin(double bnspmin) {
		this.bnspmin = bnspmin;
	}

	public double getBnspmax() {
		return bnspmax;
	}

	public void setBnspmax(double bnspmax) {
		this.bnspmax = bnspmax;
	}

	public double getBnsdmin() {
		return bnsdmin;
	}

	public void setBnsdmin(double bnsdmin) {
		this.bnsdmin = bnsdmin;
	}

	public double getBnsdmax() {
		return bnsdmax;
	}

	public void setBnsdmax(double bnsdmax) {
		this.bnsdmax = bnsdmax;
	}

	public double getBnsbmin() {
		return bnsbmin;
	}

	public void setBnsbmin(double bnsbmin) {
		this.bnsbmin = bnsbmin;
	}

	public double getBnsbmax() {
		return bnsbmax;
	}

	public void setBnsbmax(double bnsbmax) {
		this.bnsbmax = bnsbmax;
	}

	public Long getIdpartner() {
		return idpartner;
	}

	public void setIdpartner(Long idpartner) {
		this.idpartner = idpartner;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public int getActif() {
		return actif;
	}

	public void setActif(int actif) {
		this.actif = actif;
	}
}
